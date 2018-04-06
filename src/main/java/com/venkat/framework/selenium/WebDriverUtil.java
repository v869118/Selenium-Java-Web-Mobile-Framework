package com.venkat.framework.selenium;

//import java.util.List;
//import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtil
{
  private WebDriver driver;
  
  public WebDriverUtil(WebDriver driver)
  {
    this.driver = driver;
  }
  
  public void waitFor(long milliSeconds)
  {
    try
    {
      Thread.sleep(milliSeconds);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  public void waitUntilElementLocated(By by, long timeOutInSeconds)
  {
    new WebDriverWait(this.driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
  }
  
  public void waitUntilElementVisible(By by, long timeOutInSeconds)
  {
    new WebDriverWait(this.driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
  }
  
  public void waitUntilElementEnabled(By by, long timeOutInSeconds)
  {
    new WebDriverWait(this.driver, timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(by));
  }
  
  public void waitUntilElementDisabled(By by, long timeOutInSeconds)
  {
    new WebDriverWait(this.driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));
  }
  
  public void selectListItem(By by, String item)
  {
    Select dropDownList = new Select(this.driver.findElement(by));
    dropDownList.selectByVisibleText(item);
  }
  
  public Boolean objectExists(By by)
  {
    if (this.driver.findElements(by).size() == 0) {
      return Boolean.valueOf(false);
    }
    return Boolean.valueOf(true);
  }
  
  public Boolean isTextPresent(String textPattern)
  {
    if (this.driver.findElement(By.cssSelector("BODY")).getText().matches(textPattern)) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public Boolean isAlertPresent(long timeOutInSeconds)
  {
    try
    {
      new WebDriverWait(this.driver, timeOutInSeconds).until(ExpectedConditions.alertIsPresent());
      return Boolean.valueOf(true);
    }
    catch (TimeoutException ex) {}
    return Boolean.valueOf(false);
  }
}
