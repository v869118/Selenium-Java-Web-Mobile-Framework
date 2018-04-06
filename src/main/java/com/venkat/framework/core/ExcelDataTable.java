package com.venkat.framework.core;


public class ExcelDataTable
{
  private final String datatablePath;
  private final String datatableName;
  private String dataReferenceIdentifier = "#";
  private String currentTestcase;
  
  public void setDataReferenceIdentifier(String dataReferenceIdentifier)
  {
    if (dataReferenceIdentifier.length() != 1) {
      throw new FrameworkException("The data reference identifier must be a single character!");
    }
    this.dataReferenceIdentifier = dataReferenceIdentifier;
  }
  
  private int currentIteration = 0;
  
  public ExcelDataTable(String datatablePath, String datatableName)
  {
    this.datatablePath = datatablePath;
    this.datatableName = datatableName;
  }
  
  public void setCurrentRow(String currentTestcase, int currentIteration)
  {
    this.currentTestcase = currentTestcase;
    this.currentIteration = currentIteration;
  }
  
  private void checkPreRequisites()
  {
    if (this.currentTestcase == null) {
      throw new FrameworkException("ExcelDataTable.currentTestCase is not set!");
    }
    if (this.currentIteration == 0) {
      throw new FrameworkException("ExcelDataTable.currentIteration is not set!");
    }
  }
  
  public String getData(String datasheetName, String fieldName)
  {
    checkPreRequisites();
    
    ExcelDataAccess testDataAccess = new ExcelDataAccess(this.datatablePath, this.datatableName);
    testDataAccess.setDatasheetName(datasheetName);
    
    int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0, 1);
    if (rowNum == -1) {
      throw new FrameworkException("The test case \"" + this.currentTestcase + "\"" + 
        "is not found in the test data sheet \"" + datasheetName + "\"!");
    }
    rowNum = testDataAccess.getRowNum(Integer.toString(this.currentIteration), 1, rowNum);
    if (rowNum == -1) {
      throw new FrameworkException("The iteration number \"" + this.currentIteration + "\"" + 
        "of the test case \"" + this.currentTestcase + "\"" + 
        "is not found in the test data sheet \"" + datasheetName + "\"!");
    }
    String dataValue = testDataAccess.getValue(rowNum, fieldName);
    if (dataValue.startsWith(this.dataReferenceIdentifier)) {
      dataValue = getCommonData(fieldName, dataValue);
    }
    return dataValue;
  }
  
  private String getCommonData(String fieldName, String dataValue)
  {
    ExcelDataAccess commonDataAccess = new ExcelDataAccess(this.datatablePath, "Common Testdata");
    commonDataAccess.setDatasheetName("Common_Testdata");
    
    String dataReferenceId = dataValue.split(this.dataReferenceIdentifier)[1];
    
    int rowNum = commonDataAccess.getRowNum(dataReferenceId, 0, 1);
    if (rowNum == -1) {
      throw new FrameworkException("The common test data row identified by \"" + dataReferenceId + "\"" + 
        "is not found in the common test data sheet!");
    }
    dataValue = commonDataAccess.getValue(rowNum, fieldName);
    
    return dataValue;
  }
  
  public void putData(String datasheetName, String fieldName, String dataValue)
  {
    checkPreRequisites();
    
    ExcelDataAccess testDataAccess = new ExcelDataAccess(this.datatablePath, this.datatableName);
    testDataAccess.setDatasheetName(datasheetName);
    
    int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0, 1);
    if (rowNum == -1) {
      throw new FrameworkException("The test case \"" + this.currentTestcase + "\"" + 
        "is not found in the test data sheet \"" + datasheetName + "\"!");
    }
    rowNum = testDataAccess.getRowNum(Integer.toString(this.currentIteration), 1, rowNum);
    if (rowNum == -1) {
      throw new FrameworkException("The iteration number \"" + this.currentIteration + "\"" + 
        "of the test case \"" + this.currentTestcase + "\"" + 
        "is not found in the test data sheet \"" + datasheetName + "\"!");
    }
    synchronized (ExcelDataTable.class)
    {
      testDataAccess.setValue(rowNum, fieldName, dataValue);
    }
  }
  
  public String getExpectedResult(String fieldName)
  {
    checkPreRequisites();
    
    ExcelDataAccess expectedResultsAccess = new ExcelDataAccess(this.datatablePath, this.datatableName);
    expectedResultsAccess.setDatasheetName("Parametrized_Checkpoints");
    
    int rowNum = expectedResultsAccess.getRowNum(this.currentTestcase, 0, 1);
    if (rowNum == -1) {
      throw new FrameworkException("The test case \"" + this.currentTestcase + "\"" + 
        "is not found in the parametrized checkpoints sheet!");
    }
    rowNum = expectedResultsAccess.getRowNum(Integer.toString(this.currentIteration), 1, rowNum);
    if (rowNum == -1) {
      throw new FrameworkException("The iteration number \"" + this.currentIteration + "\"" + 
        "of the test case \"" + this.currentTestcase + "\"" + 
        "is not found in the parametrized checkpoints sheet!");
    }
    String dataValue = expectedResultsAccess.getValue(rowNum, fieldName);
    
    return dataValue;
  }
}
