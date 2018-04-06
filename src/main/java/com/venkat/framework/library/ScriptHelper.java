package com.venkat.framework.library;

import org.openqa.selenium.WebDriver;

import com.venkat.framework.core.*;
import com.venkat.framework.selenium.*;


/**
 * Wrapper class for common framework objects, to be used across the entire test case and dependent libraries
 * @author BNPP-TCoE
 */
public class ScriptHelper
{
	private final ExcelDataTable dataTable;
	private final SeleniumReport report;
	private final WebDriver driver;
	
	/**
	 * Constructor to initialize all the objects wrapped by the {@link ScriptHelper} class
	 * @param dataTable The {@link CraftliteDataTable} object
	 * @param report The {@link SeleniumReport} object
	 * @param driver The {@link WebDriver} object
	 */
	public ScriptHelper(ExcelDataTable dataTable, SeleniumReport report, WebDriver driver)
	{
		this.dataTable = dataTable;
		this.report = report;
		this.driver = driver;
	}
	
	/**
	 * Function to get the {@link CraftliteDataTable} object
	 * @return The {@link CraftliteDataTable} object
	 */
	public ExcelDataTable getDataTable()
	{
		return dataTable;
	}
	
	/**
	 * Function to get the {@link SeleniumReport} object
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport()
	{
		return report;
	}
	
	/**
	 * Function to get the {@link WebDriver} object
	 * @return The {@link WebDriver} object
	 */
	public WebDriver getDriver()
	{
		return driver;
	}
}