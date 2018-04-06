package com.venkat.framework.core;

import java.io.File;
import java.util.Properties;

public class TimeStamp
{
  private static volatile String m_reportPathWithTimeStamp;
  
  public static String getInstance()
  {
    if (m_reportPathWithTimeStamp == null) {
      synchronized (TimeStamp.class)
      {
        if (m_reportPathWithTimeStamp == null)
        {
          FrameworkParameters frameworkParameters = 
            FrameworkParameters.getInstance();
          if (frameworkParameters.getRelativePath() == null) {
            throw new FrameworkException("FrameworkParameters.relativePath is not set!");
          }
          if (frameworkParameters.getRunConfiguration() == null) {
            throw new FrameworkException("FrameworkParameters.runConfiguration is not set!");
          }
          Properties properties = Settings.getInstance();
          String timeStamp = 
            "Run_" + 
            Util.getCurrentFormattedTime(properties.getProperty("DateFormatString"))
            .replace(" ", "_").replace(":", "-");
          
          m_reportPathWithTimeStamp = 
            frameworkParameters.getRelativePath() + 
            Util.getFileSeparator() + "Results" + 
            Util.getFileSeparator() + frameworkParameters.getRunConfiguration() + 
            Util.getFileSeparator() + timeStamp;
          
          new File(m_reportPathWithTimeStamp).mkdirs();
        }
      }
    }
    return m_reportPathWithTimeStamp;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
}
