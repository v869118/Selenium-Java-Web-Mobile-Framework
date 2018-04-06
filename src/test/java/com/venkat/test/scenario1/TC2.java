package com.venkat.test.scenario1;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.venkat.framework.core.*;
import com.venkat.framework.library.*;
import com.venkat.framework.selenium.*;
import com.venkat.test.library.*;

/**
 * Test for login with invalid user credentials
 * @author BNPP-TCoE
 */
public class TC2 extends TestCase
{
	private FunctionalLibrary functionalLibrary;
	
	@Test
	public void runTC2()
	{
		testParameters.setCurrentTestDescription("Test for login with invalid user credentials");
		testParameters.setBrowser(Browser.Chrome);
		
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
		functionalLibrary.login();
		
		// Verify login for invalid user
		WebDriverUtil driverUtil = new WebDriverUtil(driver);
		if(!driverUtil.objectExists(By.linkText("SIGN-OFF"))) {
			report.updateTestLog("Verify Login", "Login failed for invalid user", Status.PASS);
		} else {
			report.updateTestLog("Verify Login", "Login succeeded for invalid user", Status.FAIL);
			functionalLibrary.logout();
		}
	}
	
	@Override
	public void tearDown()
	{	
		// Nothing to do
	}
}