package com.venkat.framework.selenium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

import com.venkat.framework.core.FrameworkException;
import com.venkat.framework.core.FrameworkParameters;
import com.venkat.framework.core.ReportSettings;
import com.venkat.framework.core.ReportTheme;
import com.venkat.framework.core.ReportThemeFactory;
import com.venkat.framework.core.Settings;
import com.venkat.framework.core.TimeStamp;
import com.venkat.framework.core.Util;

public class ResultSummaryManager
{
  private static SeleniumReport summaryReport;
  private static ReportSettings reportSettings;
  private static String reportPath;
  private static Date overallStartTime;
  private static Date overallEndTime;
  private Properties properties;
  private FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
  
  public void setRelativePath()
  {
    String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();   
    this.frameworkParameters.setRelativePath(relativePath);
  }
  
  public void initializeTestBatch(String runConfiguration)
  {
    overallStartTime = Util.getCurrentTime();
    
    this.properties = Settings.getInstance();
    
    this.frameworkParameters.setRunConfiguration(runConfiguration);
  }
  
  public void initializeSummaryReport(int nThreads)
  {
    initializeReportSettings();
    ReportTheme reportTheme = 
      ReportThemeFactory.getReportsTheme(ReportThemeFactory.Theme.valueOf(this.properties.getProperty("ReportsTheme")));
    
    summaryReport = new SeleniumReport(reportSettings, reportTheme);
    
    summaryReport.initialize();
    summaryReport.initializeResultSummary();
    createResultSummaryHeader(nThreads);
  }
  
  private void initializeReportSettings()
  {
    if (System.getProperty("ReportPath") != null) {
      reportPath = System.getProperty("ReportPath");
    } else {
      reportPath = TimeStamp.getInstance();
    }
    reportSettings = new ReportSettings(reportPath, "");
    
    reportSettings.setDateFormatString(this.properties.getProperty("DateFormatString"));
    reportSettings.setProjectName(this.properties.getProperty("ProjectName"));
    reportSettings.generateExcelReports = Boolean.parseBoolean(this.properties.getProperty("ExcelReport"));
    reportSettings.generateHtmlReports = Boolean.parseBoolean(this.properties.getProperty("HtmlReport"));
    reportSettings.linkTestLogsToSummary = true;
  }
  
  private void createResultSummaryHeader(int nThreads)
  {
    summaryReport.addResultSummaryHeading(reportSettings.getProjectName() + 
      " - " + " Automation Execution Result Summary");
    summaryReport.addResultSummarySubHeading("Date & Time", 
      ": " + Util.getCurrentFormattedTime(this.properties.getProperty("DateFormatString")), 
      "OnError", ": " + this.properties.getProperty("OnError"));
    summaryReport.addResultSummarySubHeading("Run Configuration", 
      ": " + this.frameworkParameters.getRunConfiguration(), 
      "No. of threads", ": " + nThreads);
    
    summaryReport.addResultSummaryTableHeadings();
  }
  
  public void setupErrorLog()
  {
    String errorLogFile = reportPath + Util.getFileSeparator() + "ErrorLog.txt";
    try
    {
      System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while setting up the Error log!");
    }
  }
  
  public void updateResultSummary(String scenarioName, String testcaseName, String testcaseDescription, String executionTime, String testStatus)
  {
    summaryReport.updateResultSummary(scenarioName, testcaseName, testcaseDescription, executionTime, testStatus);
  }
  
  public void wrapUp(Boolean testExecutedInUnitTestFramework)
  {
    overallEndTime = Util.getCurrentTime();
    String totalExecutionTime = 
      Util.getTimeDifference(overallStartTime, overallEndTime);
    summaryReport.addResultSummaryFooter(totalExecutionTime);
    if (testExecutedInUnitTestFramework.booleanValue())
    {
      File testNgResultSrc = new File(this.frameworkParameters.getRelativePath() + 
        Util.getFileSeparator() + 
        this.properties.getProperty("TestNgReportPath"));
      File testNgResultDest = new File(reportPath + 
        Util.getFileSeparator() + 
        "TestNG Results");
      try
      {
        FileUtils.copyDirectory(testNgResultSrc, testNgResultDest);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void launchResultSummary()
  {
    if (reportSettings.generateHtmlReports) {
      try
      {
        Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + 
          reportPath + "\\HTML Results\\Summary.Html");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    } else if (reportSettings.generateExcelReports) {
      try
      {
        Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + 
          reportPath + "\\Excel Results\\Summary.xls");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}
