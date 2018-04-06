package com.venkat.framework.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings
{
  private static Properties properties;
  
  public static Properties getInstance()
  {
    properties = loadFromPropertiesFile();
    return properties;
  }
  
  private static Properties loadFromPropertiesFile()
  {
    FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
    if (frameworkParameters.getRelativePath() == null) {
      throw new FrameworkException("FrameworkParameters.relativePath is not set!");
    }
    Properties properties = new Properties();
    try
    {
      properties.load(new FileInputStream(frameworkParameters.getRelativePath() + 
        Util.getFileSeparator() + "Global Settings.properties"));
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      throw new FrameworkException("FileNotFoundException while loading the Global Settings file");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("IOException while loading the Global Settings file");
    }
    return properties;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
}
