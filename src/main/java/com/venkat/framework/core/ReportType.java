package com.venkat.framework.core;

public abstract interface ReportType
{
  public abstract void initializeTestLog();
  
  public abstract void addTestLogHeading(String paramString);
  
  public abstract void addTestLogSubHeading(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void addTestLogTableHeadings();
  
  public abstract void addTestLogSection(String paramString);
  
  public abstract void addTestLogSubSection(String paramString);
  
  public abstract void updateTestLog(String paramString1, String paramString2, String paramString3, Status paramStatus, String paramString4);
  
  public abstract void addTestLogFooter(String paramString, int paramInt1, int paramInt2);
  
  public abstract void initializeResultSummary();
  
  public abstract void addResultSummaryHeading(String paramString);
  
  public abstract void addResultSummarySubHeading(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void addResultSummaryTableHeadings();
  
  public abstract void updateResultSummary(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void addResultSummaryFooter(String paramString, int paramInt1, int paramInt2);
}
