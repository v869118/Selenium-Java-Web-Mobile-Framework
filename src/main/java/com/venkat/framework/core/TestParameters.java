package com.venkat.framework.core;

public class TestParameters
{
  private final String currentScenario;
  private final String currentTestcase;
  private String currentTestDescription;
  private IterationOptions iterationMode;
  
  public String getCurrentScenario()
  {
    return this.currentScenario;
  }
  
  public String getCurrentTestcase()
  {
    return this.currentTestcase;
  }
  
  public String getCurrentTestDescription()
  {
    return this.currentTestDescription;
  }
  
  public void setCurrentTestDescription(String currentTestDescription)
  {
    this.currentTestDescription = currentTestDescription;
  }
  
  public IterationOptions getIterationMode()
  {
    return this.iterationMode;
  }
  
  public void setIterationMode(IterationOptions iterationMode)
  {
    this.iterationMode = iterationMode;
  }
  
  private int startIteration = 1;
  
  public int getStartIteration()
  {
    return this.startIteration;
  }
  
  public void setStartIteration(int startIteration)
  {
    if (startIteration > 0) {
      this.startIteration = startIteration;
    }
  }
  
  private int endIteration = 1;
  
  public int getEndIteration()
  {
    return this.endIteration;
  }
  
  public void setEndIteration(int endIteration)
  {
    if (endIteration > 0) {
      this.endIteration = endIteration;
    }
  }
  
  public TestParameters(String currentScenario, String currentTestcase)
  {
    this.currentScenario = currentScenario;
    this.currentTestcase = currentTestcase;
  }
}
