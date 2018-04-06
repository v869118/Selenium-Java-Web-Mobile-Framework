package com.venkat.framework.runner;

import java.io.File;
import java.util.Properties;

import org.openqa.selenium.Platform;

import com.venkat.framework.core.*;
import com.venkat.framework.library.DriverScript;
import com.venkat.framework.selenium.*;


/**
 * Class to manage the test execution from HP Quality Center
 * @author BNPP-TCoE
 */
public class QcTestRunner
{
	private static final FrameworkParameters frameworkParameters =
												FrameworkParameters.getInstance();
	private static SeleniumTestParameters testParameters;
	
	private QcTestRunner()
	{
		// To prevent external instantiation of this class
	}
	
	/**
	 * The entry point of the test execution from HP ALM/QC <br>
	 * Exits with a value of 0 if the test passes and 1 if the test fails
	 * @param args Command line arguments to control the test parameters (details below):<br>
	 * <b>Argument 1 :</b> The absolute path where the test report is to be stored (Mandatory)<br>
	 * <b>Argument 2 :</b> The name of the scenario which contains the test case to be executed (Mandatory)<br>
	 * <b>Argument 3 :</b> The name of the test case to be executed (Mandatory)<br>
	 * <b>Argument 4 :</b> The description of the test case to be executed (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 5 :</b> The iteration mode - RunAllIterations, RunOneIterationOnly or RunRangeOfIterations (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 6 :</b> The start iteration - applicable only for RunRangeOfIterations (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 7 :</b> The end iteration - applicable only for RunRangeOfIterations (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 8 :</b> The browser on which the test is to be executed (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 9 :</b> The browser version (Optional - Specify SKIP if not required)<br>
	 * <b>Argument 10 :</b> The platform on which the test is to be executed (Optional - Specify SKIP if not required)
	 */
    public static void main(String[] args)
	{
		if(args.length < 3) {
			System.out.println("\nError: Insufficient parameters!" +
								"\nUsage: java allocator.QcTestRunner " +
								"<report-path> " +
								"<scenario-name> <test-name> <test-description*> " +
								"<iteration-mode*> <start-iteration*> <end-iteration*> " +
								"<browser*> <browser-version*> <platform*> " +
								"\n\n * - Optional (specify SKIP if not required)");
			return;
		}
		
		setRelativePath();
		initializeTestParameters(args);
		
		String testStatus = driveExecutionFromQc();
		if(testStatus == "Passed") {
			System.exit(0);
		} else {
			System.exit(1);
		}
	}
    
    private static void setRelativePath()
	{
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if(relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}
    
    private static void initializeTestParameters(String[] args)
	{
    	System.setProperty("ReportPath", args[0]);
    	
    	Properties properties = Settings.getInstance();
    	testParameters = new SeleniumTestParameters(args[1], args[2]);
		
		if (args.length >= 4 && !args[3].equalsIgnoreCase("SKIP")) {
			testParameters.setCurrentTestDescription(args[3]);
		}
		
		if (args.length >= 5 && !args[4].equalsIgnoreCase("SKIP")) {
			testParameters.setIterationMode(IterationOptions.valueOf(args[4]));
		} else {
			testParameters.setIterationMode(IterationOptions.RunAllIterations);
		}
		
		if (args.length >= 6 && !args[5].equalsIgnoreCase("SKIP")) {
			testParameters.setStartIteration(Integer.parseInt(args[5]));
		}
		if (args.length >= 7 && !args[6].equalsIgnoreCase("SKIP")) {
			testParameters.setEndIteration(Integer.parseInt(args[6]));
		}
		
		if (args.length >= 8 && !args[7].equalsIgnoreCase("SKIP")) {
			testParameters.setBrowser(Browser.valueOf(args[7]));
		} else {
			testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
		}
		if (args.length >= 9 && !args[8].equalsIgnoreCase("SKIP")) {
			testParameters.setBrowserVersion(args[8]);
		}
		if (args.length >= 10 && !args[9].equalsIgnoreCase("SKIP")) {
			testParameters.setPlatform(Platform.valueOf(args[9]));
		} else {
			testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
		}
	}
    
    private static String driveExecutionFromQc()
    {
    	DriverScript driverScript = new DriverScript(testParameters);
		driverScript.setTestExecutedInUnitTestFramework(false);
		driverScript.setLinkScreenshotsToTestLog(false);
		driverScript.driveTestExecution();
		
		return driverScript.getTestStatus();
    }
}