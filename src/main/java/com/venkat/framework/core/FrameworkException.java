package com.venkat.framework.core;

public class FrameworkException  extends RuntimeException
{
  	private static final long serialVersionUID = -2656965737921095788L;
  	
  	public String errorName = "Error";
  
  public FrameworkException(String errorDescription)
  {
    super(errorDescription);
  }
  
  public FrameworkException(String errorName, String errorDescription)
  {
    super(errorDescription);
    this.errorName = errorName;
  }
}
