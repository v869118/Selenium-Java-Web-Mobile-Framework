package com.venkat.test.scenario2;

import org.openqa.selenium.Platform;
import org.testng.annotations.Test;

import com.venkat.framework.core.*;
import com.venkat.framework.library.*;
import com.venkat.framework.selenium.*;
import com.venkat.test.library.*;



/**
 * Test for book flight tickets and verify booking
 * @author BNPP-TCoE
 */
public class TC5 extends TestCase
{
	private FunctionalLibrary functionalLibrary;
	
	@Test
	public void runTC4()
	{
		testParameters.setBrowser(Browser.Mobile);
		testParameters.setBrowserVersion("7.0");
		testParameters.setPlatform(Platform.ANDROID);	
		testParameters.setCurrentTestDescription("Test for Expenses Tracker applicaton");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);		
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
	
	@Override
	public void setUp()
	{			
		report.addTestLogSection("Setup");		
		report.updateTestLog("Invoke Application", "Invoke the Mobile application under test", Status.DONE);	
    }
	
	@Override
	public void executeTest()
	{
		functionalLibrary = new FunctionalLibrary(scriptHelper);
		functionalLibrary.mobileLogin();
	}	
	
	@Override
	public void tearDown()
	{
		report.addTestLogSection("Teardown");		
		functionalLibrary.mobileLogout();
	}
}