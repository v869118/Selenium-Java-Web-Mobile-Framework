package com.venkat.test.library;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.venkat.framework.core.*;
import com.venkat.framework.library.*;
import com.venkat.framework.selenium.*;

/**
 * Functional Library class
 * @author BNPP-TCoE
 */
public class FunctionalLibrary extends ReusableLibrary
{
	/**
	 * Constructor to initialize the functional library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
		
	public FunctionalLibrary(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void login()
	{
		String userName = dataTable.getData("General_Data","Username");
		String password = dataTable.getData("General_Data","Password");
		
		driver.findElement(By.name("userName")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		report.updateTestLog("Login", "Enter login credentials: " +
										"Username = " + userName + ", " +
										"Password = " + password, Status.DONE);
	}
	
	public void verifyLoginValidUser()
	{
		WebDriverUtil driverUtil = new WebDriverUtil(driver);
		if(driverUtil.objectExists(By.linkText("SIGN-OFF"))) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}
	
	public void logout()
	{
		driver.findElement(By.linkText("SIGN-OFF")).click();
		report.updateTestLog("Logout", "Click the sign-off link", Status.DONE);
	}
	
	public void mobileLogin()
	{
		String userName = dataTable.getData("General_Data","Username");
		String password = dataTable.getData("General_Data","Password");
		
		driver.findElement(By.id("home_text")).click();		
		driver.findElement(By.id("edit_amount")).sendKeys("9840550576");
		driver.findElement(By.id("edit_tag")).sendKeys(userName);
		driver.findElement(By.id("edit_tag")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("edit_tag")).sendKeys(password);	
		
		report.updateTestLog("Login", "Enter login credentials: " +
										"Username = " + userName + ", " +
										"Password = " + password, Status.DONE);
	}	
	
	public void mobileLogout()
	{
		driver.findElement(By.id("com.vinsol.expensetracker:id/edit_save_entry")).click();
		report.updateTestLog("Logout", "Click the sign-off link", Status.DONE);
	}
}