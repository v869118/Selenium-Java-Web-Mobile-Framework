package com.venkat.framework.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util
{
  public static String getFileSeparator()
  {
    return System.getProperty("file.separator");
  }
  
  public static Date getCurrentTime()
  {
    Calendar calendar = Calendar.getInstance();
    return calendar.getTime();
  }
  
  public static String getCurrentFormattedTime(String dateFormatString)
  {
    DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
    Calendar calendar = Calendar.getInstance();
    return dateFormat.format(calendar.getTime());
  }
  
  public static String getFormattedTime(Date time, String dateFormatString)
  {
    DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
    return dateFormat.format(time);
  }
  
  public static String getTimeDifference(Date startTime, Date endTime)
  {
    long timeDifference = endTime.getTime() - startTime.getTime();
    timeDifference /= 1000L;
    String timeDifferenceDetailed = Long.toString(timeDifference / 60L) + " minute(s), " + 
      Long.toString(timeDifference % 60L) + " seconds";
    return timeDifferenceDetailed;
  }
}
