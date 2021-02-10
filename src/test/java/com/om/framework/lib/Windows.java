package com.om.framework.lib;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.om.framework.basetest.BaseTest;


public class Windows extends BaseTest
{
	private static boolean bStatus;
	private static Logger logger=Logger.getLogger("Windows");
	
	/**
	 * Returns the number of open WebClient windows.
	 * 
	 * @return int
	 */
	public static int getWindowCount()
	{
		Set<String> windows = driver.getWindowHandles();
		int iSize = windows.size();
		return iSize;
	}
	
	/**
	 * Get the current window title.
	 * 
	 * @author akosaraju
	 * @return String
	 */
	public static String getWindowTitle()
	{
		String sTitle = driver.getTitle();
		logger.info("Window title : "+sTitle);
		return sTitle;
	}

	/**
	 * Get all opened window titles.
	 * 
	 * @author akosaraju
	 * @return String[]
	 */
	public static String[] getWindowTitles()
	{
		Set<String> windows = driver.getWindowHandles();
		int iSize = windows.size();
		String[] arrWindows = new String[iSize];
		int iInc = 0;
		for (String handle : windows) 
		{
			driver.switchTo().window(handle);
			arrWindows[iInc] = driver.getTitle();
			logger.info("Window title : "+arrWindows[iInc]);
			iInc++;
		}
		return arrWindows;
	}

	/**
	 * Switches to window based on the given window index.
	 * 
	 * @author akosaraju
	 * @param iWindowIndex
	 * @return boolean
	 */
	public static boolean switchToWindowByIndex(int iWindowIndex)
	{
		Set<String> windows=driver.getWindowHandles();
		Iterator<String> itr=windows.iterator();
		int iSize=windows.size();
		if((iSize > 1))
		{
			if(iWindowIndex < iSize)
			{
				String[] arrWin =new String[iSize];
				int inc=0;
				while(itr.hasNext())
				{
					arrWin[inc]=itr.next().toString();
					inc++;
				}
				driver.switchTo().window(arrWin[iWindowIndex]);
				logger.info("Switched to the window by index :"+iWindowIndex);
				return true;
			}
			Messages.errorMsg = iWindowIndex+" is greater than windows count "+iSize;
			logger.warn(Messages.errorMsg);
			return false;
		}
		Messages.errorMsg = "only one window is available";
		logger.warn(Messages.errorMsg);
		return false;
	}

	/**
	 * Switches to window based on the given window title.
	 * 
	 * @author akosaraju
	 * @param sWindowName
	 * @return boolean
	 */
	public static boolean switchToWindowByTitle(String sWindowName)
	{
		String sFocusedWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		int iSize = windows.size();
		if(iSize >1)
		{
			for (String handle : windows) 
			{
				driver.switchTo().window(handle);
				if(driver.getTitle().equalsIgnoreCase(sWindowName))
				{
					logger.info("Switched to the window by title :"+sWindowName);
					return true;
				}
			}
			driver.switchTo().window(sFocusedWindow);
			Messages.errorMsg = sWindowName+" not found";
			logger.warn(Messages.errorMsg);
			return false;
		}
		Messages.errorMsg = "only one window is available";
		logger.warn(Messages.errorMsg);
		return false;
	}

	/**
	 * Switch the control to the given frame element 'objLocator'.
	 * 
	 * @author akosaraju
	 * @param objLocator
	 * @return boolean
	 */
	public static boolean switchToFrameByFrameElement(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{	
			driver.switchTo().frame(driver.findElement(objLocator));
			logger.info("Switched to the frame by element :"+objLocator);
			return true;
		}
		logger.warn("Unable to switch to the frame because : "+Messages.errorMsg);
		return false;
	}

	/**
	 * Switches to frame based on the given frame name.
	 * 
	 * @author akosaraju
	 * @param sFrameName
	 * @return boolean
	 */
	public static boolean switchToFrameByName(String sFrameName)
	{
		By objLocator = By.name(sFrameName);
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{	
			try
			{
				driver.switchTo().frame(sFrameName);
				logger.info("Switched to the frame by name :"+sFrameName);
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
			}
		}
		logger.warn("Unable to switch to the frame because : "+Messages.errorMsg);
		return false;
	}
	
	/**
	 * Switches to frame based on the given frame id
	 * 
	 * @author akosaraju
	 * @return boolean
	 */
	public static boolean switchToFrameById(String sId)
	{
		By objLocator = By.id(sId);
		bStatus = Verify.verifyElementPresent(objLocator);
		if(bStatus)
		{	
			try
			{
				driver.switchTo().frame(sId);
				logger.info("Switched to the frame by id :"+sId);
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
			}
		}
		logger.warn("Unable to switch to the frame because : "+Messages.errorMsg);
		return false;
	}
}
