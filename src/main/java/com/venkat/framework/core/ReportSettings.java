package com.venkat.framework.core;

import java.io.File;

public class ReportSettings
{
  private final String reportPath;
  private final String reportName;
  
  public String getReportPath()
  {
    return this.reportPath;
  }
  
  public String getReportName()
  {
    return this.reportName;
  }
  
  private String projectName = "";
  
  public String getProjectName()
  {
    return this.projectName;
  }
  
  public void setProjectName(String projectName)
  {
    this.projectName = projectName;
  }
  
  private int logLevel = 4;
  
  public int getLogLevel()
  {
    return this.logLevel;
  }
  
  public void setLogLevel(int logLevel)
  {
    if (logLevel < 0) {
      logLevel = 0;
    }
    if (logLevel > 5) {
      logLevel = 5;
    }
    this.logLevel = logLevel;
  }
  
  public boolean generateExcelReports = true;
  public boolean generateHtmlReports = true;
  public boolean takeScreenshotFailedStep = true;
  public boolean takeScreenshotPassedStep = false;
  public boolean linkScreenshotsToTestLog = true;
  public boolean linkTestLogsToSummary = true;
  public boolean consolidateScreenshotsInWordDoc = false;
  private String dateFormatString = "dd-MMM-yyyy hh:mm:ss a";
  
  public String getDateFormatString()
  {
    return this.dateFormatString;
  }
  
  public void setDateFormatString(String dateFormatString)
  {
    this.dateFormatString = dateFormatString;
  }
  
  public ReportSettings(String reportPath, String reportName)
  {
    boolean reportPathExists = new File(reportPath).isDirectory();
    if (!reportPathExists) {
      throw new FrameworkException("The given report path does not exist!");
    }
    this.reportPath = reportPath;
    
    this.reportName = reportName;
  }
}
