package com.venkat.test.scenario1;

import org.testng.annotations.Test;

import com.venkat.framework.core.*;
import com.venkat.framework.library.*;
import com.venkat.framework.selenium.*;
import com.venkat.test.library.*;

import org.openqa.selenium.By;



/**
 * Test for register new user and login using the registered user
 * @author BNPP-TCoE
 */
public class TC3 extends TestCase
{
	private FunctionalLibrary functionalLibrary;
	
	@Test
	public void runTC3()
	{
		testParameters.setCurrentTestDescription("Test for register new user and login using the registered user");
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
	
	@Override
	public void setUp()
	{
		functionalLibrary = new FunctionalLibrary(scriptHelper);
		report.addTestLogSection("Setup");
		
		driver.get(properties.getProperty("ApplicationUrl"));
		report.updateTestLog("Invoke Application", "Invoke the application under test @ " +
								properties.getProperty("ApplicationUrl"), Status.DONE);
	}
	
	@Override
	public void executeTest()
	{
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");
		
		// Register User
		driver.findElement(By.linkText("REGISTER")).click();
		driver.findElement(By.name("firstName")).sendKeys(dataTable.getData("RegisterUser_Data", "FirstName"));
		driver.findElement(By.name("lastName")).sendKeys(dataTable.getData("RegisterUser_Data", "LastName"));		
		driver.findElement(By.name("phone")).sendKeys(dataTable.getData("RegisterUser_Data", "Phone"));		
		driver.findElement(By.name("userName")).sendKeys(dataTable.getData("RegisterUser_Data", "Email"));	
		driver.findElement(By.name("address1")).sendKeys(dataTable.getData("RegisterUser_Data", "Address"));
		driver.findElement(By.name("city")).sendKeys(dataTable.getData("RegisterUser_Data", "City"));
		driver.findElement(By.name("state")).sendKeys(dataTable.getData("RegisterUser_Data", "State"));
		driver.findElement(By.name("postalCode")).sendKeys(dataTable.getData("RegisterUser_Data", "PostalCode"));
		driver.findElement(By.name("email")).sendKeys(dataTable.getData("General_Data", "Username"));
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("confirmPassword")).sendKeys(password);
		driver.findElement(By.name("register")).click();
		report.updateTestLog("Registration", "Enter user details for registration", Status.DONE);
		
		// Verify Registration
		WebDriverUtil driverUtil = new WebDriverUtil(driver);
		if(driverUtil.isTextPresent("^[\\s\\S]*Dear " +
				dataTable.getExpectedResult("FirstName") + " " +
				dataTable.getExpectedResult("LastName") + "[\\s\\S]*$")) {
		report.updateTestLog("Verify Registration",
									"User " + userName + " registered successfully", Status.PASS);
		} else {
			throw new FrameworkException("Verify Registration",
											"User " + userName + " registration failed");
		}
		
		// Click Sign-in
		driver.findElement(By.linkText("sign-in")).click();
		report.updateTestLog("Click sign-in", "Click the sign-in link", Status.DONE);
		
		functionalLibrary.login();
		functionalLibrary.verifyLoginValidUser();
		functionalLibrary.logout();
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do
	}
}