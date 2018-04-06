package com.venkat.framework.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class HtmlReport
  implements ReportType
{
  private String testLogPath;
  private String resultSummaryPath;
  private ReportSettings reportSettings;
  private ReportTheme reportTheme;
  private boolean isTestLogHeaderTableCreated = false;
  private boolean isTestLogMainTableCreated = false;
  private boolean isResultSummaryHeaderTableCreated = false;
  private boolean isResultSummaryMainTableCreated = false;
  private String currentSection = "";
  private String currentSubSection = "";
  private int currentContentNumber = 1;
  
  public HtmlReport(ReportSettings reportSettings, ReportTheme reportTheme)
  {
    this.reportSettings = reportSettings;
    this.reportTheme = reportTheme;
    
    this.testLogPath = 
      (reportSettings.getReportPath() + Util.getFileSeparator() + "HTML Results" + Util.getFileSeparator() + reportSettings.getReportName() + ".html");
    
    this.resultSummaryPath = 
      (reportSettings.getReportPath() + Util.getFileSeparator() + "HTML Results" + Util.getFileSeparator() + "Summary" + ".html");
  }
  
  private String getThemeCss()
  {
    String themeCss = "\t\t <style type='text/css'> \n\t\t\t body { \n\t\t\t\t background-color: " + 
    
      this.reportTheme.getContentForeColor() + "; \n" + 
      "\t\t\t\t font-family: Verdana, Geneva, sans-serif; \n" + 
      "\t\t\t\t text-align: center; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t small { \n" + 
      "\t\t\t\t font-size: 0.7em; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t table { \n" + 
      "\t\t\t\t border: 1px solid #4D7C7B; \n" + 
      "\t\t\t\t border-collapse: collapse; \n" + 
      "\t\t\t\t border-spacing: 0px; \n" + 
      "\t\t\t\t width: 95%; \n" + 
      "\t\t\t\t margin-left: auto; \n" + 
      "\t\t\t\t margin-right: auto; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t tr.heading { \n" + 
      "\t\t\t\t background-color: " + this.reportTheme.getHeadingBackColor() + "; \n" + 
      "\t\t\t\t color: " + this.reportTheme.getHeadingForeColor() + "; \n" + 
      "\t\t\t\t font-size: 0.9em; \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t tr.subheading { \n" + 
      "\t\t\t\t background-color: " + this.reportTheme.getHeadingForeColor() + "; \n" + 
      "\t\t\t\t color: " + this.reportTheme.getHeadingBackColor() + "; \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t font-size: 0.9em; \n" + 
      "\t\t\t\t text-align: justify; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t tr.section { \n" + 
      "\t\t\t\t background-color: " + this.reportTheme.getSectionBackColor() + "; \n" + 
      "\t\t\t\t color: " + this.reportTheme.getSectionForeColor() + "; \n" + 
      "\t\t\t\t cursor: pointer; \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t font-size: 0.9em; \n" + 
      "\t\t\t\t text-align: justify; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t tr.subsection { \n" + 
      "\t\t\t\t cursor: pointer; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t tr.content { \n" + 
      "\t\t\t\t background-color: " + this.reportTheme.getContentBackColor() + "; \n" + 
      "\t\t\t\t color: " + this.reportTheme.getContentForeColor() + "; \n" + 
      "\t\t\t\t font-size: 0.9em; \n" + 
      "\t\t\t\t display: table-row; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td { \n" + 
      "\t\t\t\t padding: 4px; \n" + 
      "\t\t\t\t text-align: inherit\\0/; \n" + 
      "\t\t\t\t word-wrap: break-word; \n" + 
      "\t\t\t\t max-width: 450px; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t th { \n" + 
      "\t\t\t\t padding: 4px; \n" + 
      "\t\t\t\t text-align: inherit\\0/; \n" + 
      "\t\t\t\t word-break: break-all; \n" + 
      "\t\t\t\t max-width: 450px; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.justified { \n" + 
      "\t\t\t\t text-align: justify; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.pass { \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t color: green; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.fail { \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t color: red; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.done, td.screenshot { \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t color: black; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.debug { \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t color: blue; \n" + 
      "\t\t\t } \n\n" + 
      
      "\t\t\t td.warning { \n" + 
      "\t\t\t\t font-weight: bold; \n" + 
      "\t\t\t\t color: orange; \n" + 
      "\t\t\t } \n" + 
      "\t\t </style> \n\n";
    
    return themeCss;
  }
  
  private String getJavascriptFunctions()
  {
    String javascriptFunctions = "\t\t <script> \n\t\t\t function toggleMenu(objID) { \n\t\t\t\t if (!document.getElementById) return; \n\t\t\t\t var ob = document.getElementById(objID).style; \n\t\t\t\t if(ob.display === 'none') { \n\t\t\t\t\t try { \n\t\t\t\t\t\t ob.display='table-row-group'; \n\t\t\t\t\t } catch(ex) { \n\t\t\t\t\t\t ob.display='block'; \n\t\t\t\t\t } \n\t\t\t\t } \n\t\t\t\t else { \n\t\t\t\t\t ob.display='none'; \n\t\t\t\t } \n\t\t\t } \n\t\t\t function toggleSubMenu(objId) { \n\t\t\t\t for(i=1; i<10000; i++) { \n\t\t\t\t\t var ob = document.getElementById(objId.concat(i)); \n\t\t\t\t\t if(ob === null) { \n\t\t\t\t\t\t break; \n\t\t\t\t\t } \n\t\t\t\t\t if(ob.style.display === 'none') { \n\t\t\t\t\t\t try { \n\t\t\t\t\t\t\t ob.style.display='table-row'; \n\t\t\t\t\t\t } catch(ex) { \n\t\t\t\t\t\t\t ob.style.display='block'; \n\t\t\t\t\t\t } \n\t\t\t\t\t } \n\t\t\t\t\t else { \n\t\t\t\t\t\t ob.style.display='none'; \n\t\t\t\t\t } \n\t\t\t\t } \n\t\t\t } \n\t\t </script> \n";
    
    return javascriptFunctions;
  }
  
  public void initializeTestLog()
  {
    File testLogFile = new File(this.testLogPath);
    try
    {
      testLogFile.createNewFile();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while creating HTML test log file");
    }
    FileOutputStream outputStream;
    try
    {
      outputStream = new FileOutputStream(testLogFile);
    }
    catch (FileNotFoundException e)
    {
      
      e.printStackTrace();
      throw new FrameworkException("Cannot find HTML test log file");
    }
    
    PrintStream printStream = new PrintStream(outputStream);
    
    String testLogHeadSection = "<!DOCTYPE html> \n<html> \n\t <head> \n\t\t <meta charset='UTF-8'> \n\t\t <title>" + 
    
      this.reportSettings.getProjectName() + 
      " - " + this.reportSettings.getReportName() + 
      " Automation Execution Results" + 
      "</title> \n\n" + 
      getThemeCss() + 
      getJavascriptFunctions() + 
      "\t </head> \n";
    
    printStream.println(testLogHeadSection);
    printStream.close();
  }
  
  public void addTestLogHeading(String heading)
  {
    if (!this.isTestLogHeaderTableCreated)
    {
      createTestLogHeaderTable();
      this.isTestLogHeaderTableCreated = true;
    }
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogHeading = "\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> \n\t\t\t\t\t\t " + 
      
        heading + " \n" + 
        "\t\t\t\t\t </th> \n" + 
        "\t\t\t\t </tr> \n";
      bufferedWriter.write(testLogHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding heading to HTML test log");
    }
    
  }
  
  private void createTestLogHeaderTable()
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogHeaderTable = "\t <body> \n\t\t <table id='header'> \n\t\t\t <thead> \n";
      
      bufferedWriter.write(testLogHeaderTable);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding header table to HTML test log");
    }
    
  }
  
  public void addTestLogSubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4)
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogSubHeading = "\t\t\t\t <tr class='subheading'> \n\t\t\t\t\t <th>&nbsp;" + 
        subHeading1.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading2.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading3.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading4.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t </tr> \n";
      bufferedWriter.write(testLogSubHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding sub-heading to HTML test log");
    }
    
  }
  
  private void createTestLogMainTable()
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogMainTable = "\t\t\t </thead> \n\t\t </table> \n\n\t\t <table id='main'> \n";
      
      bufferedWriter.write(testLogMainTable);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding main table to HTML test log");
    }
    
  }
  
  public void addTestLogTableHeadings()
  {
    if (!this.isTestLogMainTableCreated)
    {
      createTestLogMainTable();
      this.isTestLogMainTableCreated = true;
    }
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogTableHeading = "\t\t\t <thead> \n\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th>Step No</th> \n\t\t\t\t\t <th>Step Name</th> \n\t\t\t\t\t <th>Description</th> \n\t\t\t\t\t <th>Status</th> \n\t\t\t\t\t <th>Step Time</th> \n\t\t\t\t </tr> \n\t\t\t </thead> \n\n";
      
      bufferedWriter.write(testLogTableHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding main table headings to HTML test log");
    }
    
  }
  
  public void addTestLogSection(String section)
  {
    String testLogSection = "";
    if (!this.currentSection.equals("")) {
      testLogSection = "\t\t\t </tbody>";
    }
    this.currentSection = section.replaceAll("[^a-zA-Z0-9]", "");
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      testLogSection = testLogSection + "\t\t\t <tbody> \n\t\t\t\t <tr class='section'> \n\t\t\t\t\t <td colspan='5' onclick=\"toggleMenu('" + 
      
        this.currentSection + "')\">+ " + 
        section + "</td> \n" + 
        "\t\t\t\t </tr> \n" + 
        "\t\t\t </tbody> \n" + 
        "\t\t\t <tbody id='" + this.currentSection + "' style='display:table-row-group'> \n";
      bufferedWriter.write(testLogSection);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding section to HTML test log");
    }
    
  }
  
  public void addTestLogSubSection(String subSection)
  {
    this.currentSubSection = subSection.replaceAll("[^a-zA-Z0-9]", "");
    this.currentContentNumber = 1;
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogSubSection = "\t\t\t\t <tr class='subheading subsection'> \n\t\t\t\t\t <td colspan='5' onclick=\"toggleSubMenu('" + 
        this.currentSection + this.currentSubSection + "')\">&nbsp;+ " + 
        subSection + "</td> \n" + 
        "\t\t\t\t </tr> \n";
      bufferedWriter.write(testLogSubSection);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding sub-section to HTML test log");
    }
    
  }
  
  public void updateTestLog(String stepNumber, String stepName, String stepDescription, Status stepStatus, String screenShotName)
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testStepRow = "\t\t\t\t <tr class='content' id='" + this.currentSection + this.currentSubSection + this.currentContentNumber + "'> \n" + 
        "\t\t\t\t\t <td>" + stepNumber + "</td> \n" + 
        "\t\t\t\t\t <td class='justified'>" + stepName + "</td> \n";
      this.currentContentNumber += 1;
      switch (stepStatus)
      {
      case DEBUG: 
        if (this.reportSettings.takeScreenshotFailedStep) {
          testStepRow = testStepRow + getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
        } else {
          testStepRow = testStepRow + getTestStepWithoutScreenshot(stepDescription, stepStatus);
        }
        break;
      case FAIL: 
        if (this.reportSettings.takeScreenshotPassedStep) {
          testStepRow = testStepRow + getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
        } else {
          testStepRow = testStepRow + getTestStepWithoutScreenshot(stepDescription, stepStatus);
        }
        break;
      case PASS: 
        testStepRow = testStepRow + getTestStepWithScreenshot(stepDescription, stepStatus, screenShotName);
        break;
      case DONE: 
      default: 
        testStepRow = testStepRow + getTestStepWithoutScreenshot(stepDescription, stepStatus);
      }
      testStepRow = 
      
        testStepRow + "\t\t\t\t\t <td><small>" + Util.getCurrentFormattedTime(this.reportSettings.getDateFormatString()) + "</small>" + "</td> \n" + "\t\t\t\t </tr> \n";
      
      bufferedWriter.write(testStepRow);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while updating HTML test log");
    }
  }
  
  private String getTestStepWithScreenshot(String stepDescription, Status stepStatus, String screenShotName)
  {
    String testStepRow;
    
    if (this.reportSettings.linkScreenshotsToTestLog) {
      testStepRow = 
      
        "\t\t\t\t\t <td class='justified'>" + stepDescription + "</td> \n" + "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + "<a href='..\\Screenshots\\" + screenShotName + "'>" + stepStatus + "</a>" + "</td> \n";
    } else {
      testStepRow = 
      
        "\t\t\t\t\t <td class='justified'>" + stepDescription + " (Refer Screenshot @ " + screenShotName + ")" + "</td> \n" + "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + stepStatus + "</td> \n";
    }
    return testStepRow;
  }
  
  private String getTestStepWithoutScreenshot(String stepDescription, Status stepStatus)
  {
    String testStepRow = 
      "\t\t\t\t\t <td class='justified'>" + 
      stepDescription + 
      "</td> \n" + 
      "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" + 
      stepStatus + 
      "</td> \n";
    
    return testStepRow;
  }
  
  public void addTestLogFooter(String executionTime, int nStepsPassed, int nStepsFailed)
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.testLogPath, true));
      
      String testLogFooter = "\t\t\t </tbody> \n\t\t </table> \n\n\t\t <table id='footer'> \n\t\t\t <colgroup> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t </colgroup> \n\n\t\t\t <tfoot> \n\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th colspan='4'>Execution Duration: " + 
      
        executionTime + "</th> \n" + 
        "\t\t\t\t </tr> \n" + 
        "\t\t\t\t <tr class='subheading'> \n" + 
        "\t\t\t\t\t <td class='pass'>&nbsp;Steps passed</td> \n" + 
        "\t\t\t\t\t <td class='pass'>&nbsp;: " + nStepsPassed + "</td> \n" + 
        "\t\t\t\t\t <td class='fail'>&nbsp;Steps failed</td> \n" + 
        "\t\t\t\t\t <td class='fail'>&nbsp;: " + nStepsFailed + "</td> \n" + 
        "\t\t\t\t </tr> \n" + 
        "\t\t\t </tfoot> \n" + 
        "\t\t </table> \n" + 
        "\t </body> \n" + 
        "</html>";
      
      bufferedWriter.write(testLogFooter);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding footer to HTML test log");
    }
  }
  
  public void initializeResultSummary()
  {
    File resultSummaryFile = new File(this.resultSummaryPath);
    try
    {
      resultSummaryFile.createNewFile();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while creating HTML result summary file");
    }
    FileOutputStream outputStream;
    try
    {
      outputStream = new FileOutputStream(resultSummaryFile);
    }
    catch (FileNotFoundException e)
    {
      
      e.printStackTrace();
      throw new FrameworkException("Cannot find HTML result summary file");
    }
    
    PrintStream printStream = new PrintStream(outputStream);
    
    String resultSummaryHeader = "<!DOCTYPE html> \n<html> \n\t <head> \n\t\t <meta charset='UTF-8'> \n\t\t <title>" + 
    
      this.reportSettings.getProjectName() + 
      " - Automation Execution Results Summary" + 
      "</title> \n\n" + 
      getThemeCss() + 
      getJavascriptFunctions() + 
      "\t </head> \n";
    
    printStream.println(resultSummaryHeader);
    printStream.close();
  }
  
  public void addResultSummaryHeading(String heading)
  {
    if (!this.isResultSummaryHeaderTableCreated)
    {
      createResultSummaryHeaderTable();
      this.isResultSummaryHeaderTableCreated = true;
    }
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummaryHeading = "\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> \n\t\t\t\t\t\t " + 
      
        heading + " \n" + 
        "\t\t\t\t\t </th> \n" + 
        "\t\t\t\t </tr> \n";
      bufferedWriter.write(resultSummaryHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding heading to HTML result summary");
    }
   
  }
  
  private void createResultSummaryHeaderTable()
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummaryHeaderTable = "\t <body> \n\t\t <table id='header'> \n\t\t\t <thead> \n";
      
      bufferedWriter.write(resultSummaryHeaderTable);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding header table to HTML result summary");
    }
    
  }
  
  public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4)
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummarySubHeading = "\t\t\t\t <tr class='subheading'> \n\t\t\t\t\t <th>&nbsp;" + 
        subHeading1.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading2.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading3.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t\t <th>&nbsp;" + subHeading4.replace(" ", "&nbsp;") + "</th> \n" + 
        "\t\t\t\t </tr> \n";
      bufferedWriter.write(resultSummarySubHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding sub-heading to HTML result summary");
    }
   
  }
  
  private void createResultSummaryMainTable()
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummaryMainTable = "\t\t\t </thead> \n\t\t </table> \n\n\t\t <table id='main'> \n\t\t\t <colgroup> \n";
      
      bufferedWriter.write(resultSummaryMainTable);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding main table to HTML result summary");
    }
    
  }
  
  public void addResultSummaryTableHeadings()
  {
    if (!this.isResultSummaryMainTableCreated)
    {
      createResultSummaryMainTable();
      this.isResultSummaryMainTableCreated = true;
    }
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummaryTableHeading = "\t\t\t <thead> \n\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th>Test Scenario</th> \n\t\t\t\t\t <th>Test Case</th> \n\t\t\t\t\t <th>Test Description</th> \n\t\t\t\t\t <th>Execution Time</th> \n\t\t\t\t\t <th>Test Status</th> \n\t\t\t\t </tr> \n\t\t\t </thead> \n\n";
      
      bufferedWriter.write(resultSummaryTableHeading);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding main table headings to HTML result summary");
    }
   
  }
  
  public void updateResultSummary(String scenarioName, String testcaseName, String testcaseDescription, String executionTime, String testStatus)
  {
    try
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      String testcaseRow;
      
      if (this.reportSettings.linkTestLogsToSummary) {
        testcaseRow = 
        
          "\t\t\t\t <tr class='content' > \n\t\t\t\t\t <td class='justified'>" + scenarioName + "</td> \n" + "\t\t\t\t\t <td class='justified'><a href='" + scenarioName + "_" + testcaseName + ".html' " + "target='about_blank'>" + testcaseName + "</a>" + "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n" + "\t\t\t\t\t <td>" + executionTime + "</td> \n";
      } else {
        testcaseRow = 
        
          "\t\t\t\t <tr class='content' > \n\t\t\t\t\t <td class='justified'>" + scenarioName + "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseName + "</td> \n" + "\t\t\t\t\t <td class='justified'>" + testcaseDescription + "</td> \n" + "\t\t\t\t\t <td>" + executionTime + "</td> \n";
      }
      if (testStatus.equalsIgnoreCase("passed")) {
        testcaseRow = 
          testcaseRow + "\t\t\t\t\t <td class='pass'>" + testStatus + "</td> \n" + "\t\t\t\t </tr> \n";
      } else {
        testcaseRow = 
          testcaseRow + "\t\t\t\t\t <td class='fail'>" + testStatus + "</td> \n" + "\t\t\t\t </tr> \n";
      }
      bufferedWriter.write(testcaseRow);
      bufferedWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while updating HTML result summary");
    }
  }
  
  public void addResultSummaryFooter(String totalExecutionTime, int nTestsPassed, int nTestsFailed)
  {
    try
    {
      BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(this.resultSummaryPath, true));
      
      String resultSummaryFooter = "\t\t\t </tbody> \n\t\t </table> \n\n\t\t <table id='footer'> \n\t\t\t <colgroup> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t\t <col style='width: 25%' /> \n\t\t\t </colgroup> \n\n\t\t\t <tfoot> \n\t\t\t\t <tr class='heading'> \n\t\t\t\t\t <th colspan='4'>Total Duration: " + 
      
        totalExecutionTime + "</th> \n" + 
        "\t\t\t\t </tr> \n" + 
        "\t\t\t\t <tr class='subheading'> \n" + 
        "\t\t\t\t\t <td class='pass'>&nbsp;Tests passed</td> \n" + 
        "\t\t\t\t\t <td class='pass'>&nbsp;: " + nTestsPassed + "</td> \n" + 
        "\t\t\t\t\t <td class='fail'>&nbsp;Tests failed</td> \n" + 
        "\t\t\t\t\t <td class='fail'>&nbsp;: " + nTestsFailed + "</td> \n" + 
        "\t\t\t\t </tr> \n" + 
        "\t\t\t </tfoot> \n" + 
        "\t\t </table> \n" + 
        "\t </body> \n" + 
        "</html>";
      
      bufferWriter.write(resultSummaryFooter);
      bufferWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while adding footer to HTML result summary");
    }
  }
}
