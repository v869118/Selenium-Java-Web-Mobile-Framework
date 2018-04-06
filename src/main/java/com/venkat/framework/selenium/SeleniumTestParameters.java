package com.venkat.framework.selenium;

import org.openqa.selenium.Platform;

import com.venkat.framework.core.TestParameters;

public class SeleniumTestParameters
  extends TestParameters
{
  private Browser browser;
  private String browserVersion;
  private Platform platform;
  
  public SeleniumTestParameters(String currentScenario, String currentTestcase)
  {
    super(currentScenario, currentTestcase);
  }
  
  public Browser getBrowser()
  {
    return this.browser;
  }
  
  public void setBrowser(Browser browser)
  {
    this.browser = browser;
  }
  
  public String getBrowserVersion()
  {
    return this.browserVersion;
  }
  
  public void setBrowserVersion(String version)
  {
    this.browserVersion = version;
  }
  
  public Platform getPlatform()
  {
    return this.platform;
  }
  
  public void setPlatform(Platform platform)
  {
    this.platform = platform;
  }
}
