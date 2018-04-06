package com.venkat.framework.core;

public class ExcelReport
  implements ReportType
{
  private ExcelDataAccess testLogAccess;
  private ExcelDataAccess resultSummaryAccess;
  private ReportSettings reportSettings;
  private ReportTheme reportTheme;
  private ExcelCellFormatting cellFormatting = new ExcelCellFormatting();
  private int currentSectionRowNum = 0;
  private int currentSubSectionRowNum = 0;
  
  public ExcelReport(ReportSettings reportSettings, ReportTheme reportTheme)
  {
    this.reportSettings = reportSettings;
    this.reportTheme = reportTheme;
    
    this.testLogAccess = 
      new ExcelDataAccess(reportSettings.getReportPath() + 
      Util.getFileSeparator() + "Excel Results", 
      reportSettings.getReportName());
    
    this.resultSummaryAccess = 
      new ExcelDataAccess(reportSettings.getReportPath() + 
      Util.getFileSeparator() + "Excel Results", 
      "Summary");
  }
  
  public void initializeTestLog()
  {
    this.testLogAccess.createWorkbook();
    this.testLogAccess.addSheet("Cover_Page");
    this.testLogAccess.addSheet("Test_Log");
    
    initializeTestLogColorPalette();
    
    this.testLogAccess.setRowSumsBelow(false);
  }
  
  private void initializeTestLogColorPalette()
  {
    this.testLogAccess.setCustomPaletteColor((short)8, this.reportTheme.getHeadingBackColor());
    this.testLogAccess.setCustomPaletteColor((short)9, this.reportTheme.getHeadingForeColor());
    this.testLogAccess.setCustomPaletteColor((short)10, this.reportTheme.getSectionBackColor());
    this.testLogAccess.setCustomPaletteColor((short)11, this.reportTheme.getSectionForeColor());
    this.testLogAccess.setCustomPaletteColor((short)12, this.reportTheme.getContentBackColor());
    this.testLogAccess.setCustomPaletteColor((short)13, this.reportTheme.getContentForeColor());
    this.testLogAccess.setCustomPaletteColor((short)14, "#008000");
    this.testLogAccess.setCustomPaletteColor((short)15, "#FF0000");
    this.testLogAccess.setCustomPaletteColor((short)16, "#FF8000");
    this.testLogAccess.setCustomPaletteColor((short)17, "#000000");
    this.testLogAccess.setCustomPaletteColor((short)18, "#00FF80");
  }
  
  public void addTestLogHeading(String heading)
  {
    this.testLogAccess.setDatasheetName("Cover_Page");
    int rowNum = this.testLogAccess.getLastRowNum();
    if (rowNum != 0) {
      rowNum = this.testLogAccess.addRow();
    }
    this.cellFormatting.setFontName("Copperplate Gothic Bold");
    this.cellFormatting.setFontSize((short)12);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.testLogAccess.setValue(rowNum, 0, heading, this.cellFormatting);
    this.testLogAccess.mergeCells(rowNum, rowNum, 0, 4);
  }
  
  public void addTestLogSubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4)
  {
    this.testLogAccess.setDatasheetName("Cover_Page");
    int rowNum = this.testLogAccess.addRow();
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)9);
    this.cellFormatting.setForeColorIndex((short)8);
    
    this.testLogAccess.setValue(rowNum, 0, subHeading1, this.cellFormatting);
    this.testLogAccess.setValue(rowNum, 1, subHeading2, this.cellFormatting);
    this.testLogAccess.setValue(rowNum, 2, "", this.cellFormatting);
    this.testLogAccess.setValue(rowNum, 3, subHeading3, this.cellFormatting);
    this.testLogAccess.setValue(rowNum, 4, subHeading4, this.cellFormatting);
  }
  
  public void addTestLogTableHeadings()
  {
    this.testLogAccess.setDatasheetName("Test_Log");
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.testLogAccess.addColumn("Step_No", this.cellFormatting);
    this.testLogAccess.addColumn("Step_Name", this.cellFormatting);
    this.testLogAccess.addColumn("Description", this.cellFormatting);
    this.testLogAccess.addColumn("Status", this.cellFormatting);
    this.testLogAccess.addColumn("Step_Time", this.cellFormatting);
  }
  
  public void addTestLogSection(String section)
  {
    this.testLogAccess.setDatasheetName("Test_Log");
    int rowNum = this.testLogAccess.addRow();
    if (this.currentSubSectionRowNum != 0) {
      this.testLogAccess.groupRows(this.currentSubSectionRowNum, rowNum - 1);
    }
    if (this.currentSectionRowNum != 0) {
      this.testLogAccess.groupRows(this.currentSectionRowNum, rowNum - 1);
    }
    this.currentSectionRowNum = (rowNum + 1);
    this.currentSubSectionRowNum = 0;
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)10);
    this.cellFormatting.setForeColorIndex((short)11);
    
    this.testLogAccess.setValue(rowNum, 0, section, this.cellFormatting);
    this.testLogAccess.mergeCells(rowNum, rowNum, 0, 4);
  }
  
  public void addTestLogSubSection(String subSection)
  {
    this.testLogAccess.setDatasheetName("Test_Log");
    int rowNum = this.testLogAccess.addRow();
    if (this.currentSubSectionRowNum != 0) {
      this.testLogAccess.groupRows(this.currentSubSectionRowNum, rowNum - 1);
    }
    this.currentSubSectionRowNum = (rowNum + 1);
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)9);
    this.cellFormatting.setForeColorIndex((short)8);
    
    this.testLogAccess.setValue(rowNum, 0, " " + subSection, this.cellFormatting);
    this.testLogAccess.mergeCells(rowNum, rowNum, 0, 4);
  }
  
  public void updateTestLog(String stepNumber, String stepName, String stepDescription, Status stepStatus, String screenShotName)
  {
    this.testLogAccess.setDatasheetName("Test_Log");
    int rowNum = this.testLogAccess.addRow();
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.setBackColorIndex((short)12);
    
    boolean stepContainsScreenshot = processStatusColumn(stepStatus);
    
    this.cellFormatting.centred = true;
    this.cellFormatting.bold = true;
    int columnNum = this.testLogAccess.getColumnNum("Status", 0);
    this.testLogAccess.setValue(rowNum, columnNum, stepStatus.toString(), this.cellFormatting);
    
    this.cellFormatting.setForeColorIndex((short)13);
    this.cellFormatting.bold = false;
    this.testLogAccess.setValue(rowNum, "Step_No", stepNumber, this.cellFormatting);
    this.testLogAccess.setValue(rowNum, "Step_Time", Util.getCurrentFormattedTime(this.reportSettings.getDateFormatString()), this.cellFormatting);
    
    this.cellFormatting.centred = false;
    this.testLogAccess.setValue(rowNum, "Step_Name", stepName, this.cellFormatting);
    if (stepContainsScreenshot)
    {
      if (this.reportSettings.linkScreenshotsToTestLog)
      {
        this.testLogAccess.setHyperlink(rowNum, columnNum, "..\\Screenshots\\" + screenShotName);
        
        this.testLogAccess.setValue(rowNum, "Description", stepDescription, this.cellFormatting);
      }
      else
      {
        this.testLogAccess.setValue(rowNum, "Description", 
          stepDescription + " (Refer screenshot @ " + screenShotName + ")", 
          this.cellFormatting);
      }
    }
    else {
      this.testLogAccess.setValue(rowNum, "Description", stepDescription, this.cellFormatting);
    }
  }
  
  private boolean processStatusColumn(Status stepStatus)
  {
    boolean stepContainsScreenshot = false;
    switch (stepStatus)
    {
    case FAIL: 
      this.cellFormatting.setForeColorIndex((short)14);
      stepContainsScreenshot = this.reportSettings.takeScreenshotPassedStep;
      break;
    case DEBUG: 
      this.cellFormatting.setForeColorIndex((short)15);
      stepContainsScreenshot = this.reportSettings.takeScreenshotFailedStep;
      break;
    case DONE: 
      this.cellFormatting.setForeColorIndex((short)16);
      stepContainsScreenshot = this.reportSettings.takeScreenshotFailedStep;
      break;
    case SCREENSHOT: 
      this.cellFormatting.setForeColorIndex((short)17);
      stepContainsScreenshot = false;
      break;
    case PASS: 
      this.cellFormatting.setForeColorIndex((short)17);
      stepContainsScreenshot = true;
      break;
    case WARNING: 
      this.cellFormatting.setForeColorIndex((short)18);
      stepContainsScreenshot = false;
    }
    return stepContainsScreenshot;
  }
  
  public void addTestLogFooter(String executionTime, int nStepsPassed, int nStepsFailed)
  {
    this.testLogAccess.setDatasheetName("Test_Log");
    int rowNum = this.testLogAccess.addRow();
    if (this.currentSubSectionRowNum != 0) {
      this.testLogAccess.groupRows(this.currentSubSectionRowNum, rowNum - 1);
    }
    if (this.currentSectionRowNum != 0) {
      this.testLogAccess.groupRows(this.currentSectionRowNum, rowNum - 1);
    }
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.testLogAccess.setValue(rowNum, 0, "Execution Duration: " + executionTime, this.cellFormatting);
    this.testLogAccess.mergeCells(rowNum, rowNum, 0, 4);
    
    rowNum = this.testLogAccess.addRow();
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)9);
    
    this.cellFormatting.setForeColorIndex((short)14);
    this.testLogAccess.setValue(rowNum, "Step_No", "Steps passed", this.cellFormatting);
    this.testLogAccess.setValue(rowNum, "Step_Name", ": " + nStepsPassed, this.cellFormatting);
    this.cellFormatting.setForeColorIndex((short)8);
    this.testLogAccess.setValue(rowNum, "Description", "", this.cellFormatting);
    this.cellFormatting.setForeColorIndex((short)15);
    this.testLogAccess.setValue(rowNum, "Status", "Steps failed", this.cellFormatting);
    this.testLogAccess.setValue(rowNum, "Step_Time", ": " + nStepsFailed, this.cellFormatting);
    
    wrapUpTestLog();
  }
  
  private void wrapUpTestLog()
  {
    this.testLogAccess.autoFitContents(0, 4);
    this.testLogAccess.addOuterBorder(0, 4);
    
    this.testLogAccess.setDatasheetName("Cover_Page");
    this.testLogAccess.autoFitContents(0, 4);
    this.testLogAccess.addOuterBorder(0, 4);
  }
  
  public void initializeResultSummary()
  {
    this.resultSummaryAccess.createWorkbook();
    this.resultSummaryAccess.addSheet("Cover_Page");
    this.resultSummaryAccess.addSheet("Result_Summary");
    
    initializeResultSummaryColorPalette();
  }
  
  private void initializeResultSummaryColorPalette()
  {
    this.resultSummaryAccess.setCustomPaletteColor((short)8, this.reportTheme.getHeadingBackColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)9, this.reportTheme.getHeadingForeColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)10, this.reportTheme.getSectionBackColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)11, this.reportTheme.getSectionForeColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)12, this.reportTheme.getContentBackColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)13, this.reportTheme.getContentForeColor());
    this.resultSummaryAccess.setCustomPaletteColor((short)14, "#008000");
    this.resultSummaryAccess.setCustomPaletteColor((short)15, "#FF0000");
  }
  
  public void addResultSummaryHeading(String heading)
  {
    this.resultSummaryAccess.setDatasheetName("Cover_Page");
    int rowNum = this.resultSummaryAccess.getLastRowNum();
    if (rowNum != 0) {
      rowNum = this.resultSummaryAccess.addRow();
    }
    this.cellFormatting.setFontName("Copperplate Gothic Bold");
    this.cellFormatting.setFontSize((short)12);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.resultSummaryAccess.setValue(rowNum, 0, heading, this.cellFormatting);
    this.resultSummaryAccess.mergeCells(rowNum, rowNum, 0, 4);
  }
  
  public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4)
  {
    this.resultSummaryAccess.setDatasheetName("Cover_Page");
    int rowNum = this.resultSummaryAccess.addRow();
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)9);
    this.cellFormatting.setForeColorIndex((short)8);
    
    this.resultSummaryAccess.setValue(rowNum, 0, subHeading1, this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, 1, subHeading2, this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, 2, "", this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, 3, subHeading3, this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, 4, subHeading4, this.cellFormatting);
  }
  
  public void addResultSummaryTableHeadings()
  {
    this.resultSummaryAccess.setDatasheetName("Result_Summary");
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.resultSummaryAccess.addColumn("Test_Scenario", this.cellFormatting);
    this.resultSummaryAccess.addColumn("Test_Case", this.cellFormatting);
    this.resultSummaryAccess.addColumn("Test_Description", this.cellFormatting);
    this.resultSummaryAccess.addColumn("Execution_Time", this.cellFormatting);
    this.resultSummaryAccess.addColumn("Test_Status", this.cellFormatting);
  }
  
  public void updateResultSummary(String scenarioName, String testcaseName, String testcaseDescription, String executionTime, String testStatus)
  {
    this.resultSummaryAccess.setDatasheetName("Result_Summary");
    int rowNum = this.resultSummaryAccess.addRow();
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.setBackColorIndex((short)12);
    this.cellFormatting.setForeColorIndex((short)13);
    
    this.cellFormatting.centred = false;
    this.cellFormatting.bold = false;
    this.resultSummaryAccess.setValue(rowNum, "Test_Scenario", scenarioName, this.cellFormatting);
    
    int columnNum = this.resultSummaryAccess.getColumnNum("Test_Case", 0);
    this.resultSummaryAccess.setValue(rowNum, columnNum, testcaseName, this.cellFormatting);
    if (this.reportSettings.linkTestLogsToSummary) {
      this.resultSummaryAccess.setHyperlink(rowNum, columnNum, scenarioName + "_" + testcaseName + ".xls");
    }
    this.resultSummaryAccess.setValue(rowNum, "Test_Description", testcaseDescription, this.cellFormatting);
    
    this.cellFormatting.centred = true;
    this.resultSummaryAccess.setValue(rowNum, "Execution_Time", executionTime, this.cellFormatting);
    
    this.cellFormatting.bold = true;
    if (testStatus.equalsIgnoreCase("Passed")) {
      this.cellFormatting.setForeColorIndex((short)14);
    }
    if (testStatus.equalsIgnoreCase("Failed")) {
      this.cellFormatting.setForeColorIndex((short)15);
    }
    this.resultSummaryAccess.setValue(rowNum, "Test_Status", testStatus, this.cellFormatting);
  }
  
  public void addResultSummaryFooter(String totalExecutionTime, int nTestsPassed, int nTestsFailed)
  {
    this.resultSummaryAccess.setDatasheetName("Result_Summary");
    int rowNum = this.resultSummaryAccess.addRow();
    
    this.cellFormatting.setFontName("Verdana");
    this.cellFormatting.setFontSize((short)10);
    this.cellFormatting.bold = true;
    this.cellFormatting.centred = true;
    this.cellFormatting.setBackColorIndex((short)8);
    this.cellFormatting.setForeColorIndex((short)9);
    
    this.resultSummaryAccess.setValue(rowNum, 0, "Total Duration: " + 
      totalExecutionTime, this.cellFormatting);
    this.resultSummaryAccess.mergeCells(rowNum, rowNum, 0, 4);
    
    rowNum = this.resultSummaryAccess.addRow();
    this.cellFormatting.centred = false;
    this.cellFormatting.setBackColorIndex((short)9);
    
    this.cellFormatting.setForeColorIndex((short)14);
    this.resultSummaryAccess.setValue(rowNum, "Test_Scenario", "Tests passed", this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, "Test_Case", ": " + nTestsPassed, this.cellFormatting);
    this.cellFormatting.setForeColorIndex((short)8);
    this.resultSummaryAccess.setValue(rowNum, "Test_Description", "", this.cellFormatting);
    this.cellFormatting.setForeColorIndex((short)15);
    this.resultSummaryAccess.setValue(rowNum, "Execution_Time", "Tests failed", this.cellFormatting);
    this.resultSummaryAccess.setValue(rowNum, "Test_Status", ": " + nTestsFailed, this.cellFormatting);
    
    wrapUpResultSummary();
  }
  
  private void wrapUpResultSummary()
  {
    this.resultSummaryAccess.autoFitContents(0, 4);
    this.resultSummaryAccess.addOuterBorder(0, 4);
    
    this.resultSummaryAccess.setDatasheetName("Cover_Page");
    this.resultSummaryAccess.autoFitContents(0, 4);
    this.resultSummaryAccess.addOuterBorder(0, 4);
  }
}
