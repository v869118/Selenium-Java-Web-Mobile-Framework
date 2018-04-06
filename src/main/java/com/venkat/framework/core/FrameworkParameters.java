package com.venkat.framework.core;

public class FrameworkParameters
{
  private String relativePath;
  private String runConfiguration;
  
  public String getRelativePath()
  {
    return this.relativePath;
  }
  
  public void setRelativePath(String relativePath)
  {
    this.relativePath = relativePath;
  }
  
  public String getRunConfiguration()
  {
    return this.runConfiguration;
  }
  
  public void setRunConfiguration(String runConfiguration)
  {
    this.runConfiguration = runConfiguration;
  }
  
  private boolean stopExecution = false;
  
  public boolean getStopExecution()
  {
    return this.stopExecution;
  }
  
  public void setStopExecution(boolean stopExecution)
  {
    this.stopExecution = stopExecution;
  }
  
  private static final FrameworkParameters frameworkParameters = new FrameworkParameters();
  
  public static FrameworkParameters getInstance()
  {
    return frameworkParameters;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
}
