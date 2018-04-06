package com.venkat.framework.selenium;

import java.net.URL;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SwipeableWebDriver extends RemoteWebDriver implements HasTouchScreen  
{
	  private final RemoteTouchScreen touch;

	  public SwipeableWebDriver(final URL remoteAddress, final Capabilities desiredCapabilities)
	  {
	    super(remoteAddress, desiredCapabilities);
	    touch = new RemoteTouchScreen(getExecuteMethod());
	  }

	  public TouchScreen getTouch()
	  {
		  return touch;
	  }

}
