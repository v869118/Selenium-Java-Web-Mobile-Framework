package com.venkat.test.scenario2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.venkat.framework.core.*;
import com.venkat.framework.library.*;
import com.venkat.framework.selenium.*;
import com.venkat.test.library.*;



/**
 * Test for book flight tickets and verify booking
 * @author BNPP-TCoE
 */
public class TC4 extends TestCase
{
	private FunctionalLibrary functionalLibrary;
	
	@Test
	public void runTC4()
	{
		testParameters.setCurrentTestDescription("Test for book flight tickets and verify booking");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		//testParameters.setBrowser(Browser.InternetExplorer);
		
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
		
		functionalLibrary.login();
	}
	
	@Override
	public void executeTest()
	{
		findFlights();
		selectFlights();
		bookFlights();
		
		// Verify Booking
		WebDriverUtil driverUtil = new WebDriverUtil(driver); 
		if(driverUtil.isTextPresent("^[\\s\\S]*Your itinerary has been booked![\\s\\S]*$")) {
			report.updateTestLog("Verify Booking", "Tickets booked successfully", Status.PASS);
			
			//WebElement flightConfirmation = driver.findElement(By.xpath("//font/font/b/font"));
			WebElement flightConfirmation =
								driver.findElement(By.cssSelector("font > font > b > font"));
			
			String flightConfirmationNumber = flightConfirmation.getText();
			flightConfirmationNumber = flightConfirmationNumber.split("#")[1].trim();
			dataTable.putData("Flights_Data", "FlightConfirmationNumber", flightConfirmationNumber);
			report.updateTestLog("Flight Confirmation",
					"The flight confirmation number is " + flightConfirmationNumber,
					Status.DONE);
		} else {
			report.updateTestLog("Verify Booking", "Tickets booking failed", Status.FAIL);
		}
		
		backToFlights();
	}
	
	private void findFlights()
	{
		driver.findElement(By.name("passCount")).sendKeys((dataTable.getData("Passenger_Data", "PassengerCount")));
		driver.findElement(By.name("fromPort")).sendKeys((dataTable.getData("Flights_Data", "FromPort")));
		driver.findElement(By.name("fromMonth")).sendKeys((dataTable.getData("Flights_Data", "FromMonth")));
		driver.findElement(By.name("fromDay")).sendKeys((dataTable.getData("Flights_Data", "FromDay")));
		driver.findElement(By.name("toPort")).sendKeys((dataTable.getData("Flights_Data", "ToPort")));
		driver.findElement(By.name("toMonth")).sendKeys((dataTable.getData("Flights_Data", "ToMonth")));
		driver.findElement(By.name("toDay")).sendKeys((dataTable.getData("Flights_Data", "ToDay")));
		driver.findElement(By.name("airline")).sendKeys((dataTable.getData("Flights_Data", "Airline")));
		driver.findElement(By.name("findFlights")).click();
		report.updateTestLog("Find Flights", "Search for flights using given test data", Status.DONE);
	}
	
	private void selectFlights()
	{
		driver.findElement(By.name("reserveFlights")).click();
		report.updateTestLog("Select Flights", "Select the first available flights", Status.DONE);
	}
	
	private void bookFlights()
	{
		String[] passengerFirstNames = dataTable.getData("Passenger_Data", "PassengerFirstNames").split(",");
		String[] passengerLastNames = dataTable.getData("Passenger_Data", "PassengerLastNames").split(",");
		int passengerCount = Integer.parseInt(dataTable.getData("Passenger_Data", "PassengerCount"));
		for(int i=0; i<passengerCount; i++) {
			driver.findElement(By.name("passFirst" + i)).sendKeys(passengerFirstNames[i]);
			driver.findElement(By.name("passLast" + i)).sendKeys(passengerLastNames[i]);
		}
		driver.findElement(By.name("creditCard")).sendKeys(dataTable.getData("Passenger_Data", "CreditCard"));
		driver.findElement(By.name("creditnumber")).sendKeys(dataTable.getData("Passenger_Data", "CreditNumber"));
		driver.findElement(By.name("buyFlights")).click();
		report.updateTestLog("Book Tickets", "Enter passenger details and book tickets", Status.DONE);
	}
	
	private void backToFlights()
	{
		driver.findElement(By.xpath("//a/img")).click();
	}
	
	@Override
	public void tearDown()
	{
		report.addTestLogSection("Teardown");
		
		functionalLibrary.logout();
	}
}