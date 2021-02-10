package com.om.framework.lib;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.om.framework.basetest.BaseTest;

public class Wait extends BaseTest 
{

	private static WebDriverWait wait;
	private static boolean bStatus;
	private static Logger logger=Logger.getLogger("Wait");


	/********************************************************************************
	Function Name 					: waitForGivenTimeOut
	Description						: Wiats for the tive time out
	Parameters						: iTimeOut (milliseconds)
	Usage							: waitForGivenTimeOut(iTimeOut)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static void waitForGivenTimeOut(long iTimeOut) throws InterruptedException
	{
		Thread.sleep(iTimeOut);
		logger.info("Waited :"+iTimeOut + " milli seconds");
	}


	/*******************************************************************************
	Function Name 					: waitForElementVisibility
	Description						: Wait for the given element to be visible with a maximum time out of 'iTimOut" seconds.
	Parameters						: objLocator, iTimeout(seconds)
	Usage							: bStatus = waitForElementVisibility(objLocator, iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/

	public static boolean waitForElementVisibility(By objLocator,long iTimeout)
	{
		try{
			wait = new WebDriverWait(driver,iTimeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			logger.info("element "+objLocator+" is visible after waiting.");
			return true;
		}
		catch(Exception e)
		{	
			Messages.errorMsg = e.getMessage();
			logger.warn("element "+objLocator+" is not present after waiting "+iTimeout+" secs.");
			return false;
		}
	}

	/*******************************************************************************
	Function Name 					: waitForTextVisible
	Description						: Wait for the given text to be visible on screen with a maximum time out of 'iTimOut".
	Parameters						: sText, iTimeout(Seconds)
	Usage							: bStatus = waitForTextVisible(sText, iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean waitForTextVisible(String sText,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyTextVisible(sText);
			if(bStatus)
			{
				logger.info("Text '"+sText+"' is present after waiting .");
				return true;
			}
		}	
		Messages.errorMsg="Text '"+sText+"' not found in the current page after waiting "+iTimeout+"secs";
		logger.warn(Messages.errorMsg);
		return false; 
	}

	/*******************************************************************************
	Function Name 					: waitForTextVisible
	Description						: Wait for the given text to be visible in the given element with a maximum time out of 'iTimOut".
	Parameters						: objLocator, sText, iTimeout(Seconds)
	Usage							: bStatus = waitForTextVisible(objLocator,sText, iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean waitForTextVisible(By objLocator,String sText,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyTextVisible(objLocator ,sText);
			if(bStatus)
			{
				logger.info("Text '"+sText+"' is present after waiting .");
				return true;
			}
		}
		Messages.errorMsg="Text '"+sText+"' not found in the current page after waiting "+iTimeout+"secs";
		logger.warn(Messages.errorMsg);
		return false; 
	}

	/*******************************************************************************
	Function Name 					: waitForAlert
	Description						: Wait for alert on screen with time out of 'iTimOut".
	Parameters						: iTimeout(Seconds)
	Usage							: bStatus = waitForAlert(iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean waitForAlert(long iTimeout) 
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			try {
				driver.switchTo().alert();
				logger.info("Alert present");
				return true;
			} catch (NoAlertPresentException e) {
				Messages.errorMsg = e.getMessage();
				continue;
			}
		}
		return false;
	}

	/*******************************************************************************
	Function Name 					: waitForJQueryProcessing
	Description						: Wait for jquery processing bar to process on screen with time out of 'iTimOut".
	Parameters						: iTimeout(Seconds)
	Usage							: bStatus = waitForJQueryProcessing(iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean waitForJQueryProcessing(long iTimeout)
	{
		boolean jQcondition = false;
		try{	
			wait = new WebDriverWait(driver,iTimeout);
			wait.until(new ExpectedCondition<Boolean>() 
			{
				@Override
				public Boolean apply(WebDriver wDriver) 
				{
					return (Boolean) ((JavascriptExecutor) wDriver).executeScript("return jQuery.active == 0");
				}

			});
			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != undefined && jQuery.active === 0");
			if(!jQcondition)
			{
				Messages.errorMsg = "couldn't wait for Ajax to load completely after waiting "+iTimeout+"Secs";
				logger.warn(Messages.errorMsg);
				return false;
			}
			return true;
		} 
		catch (Exception e) 
		{
			Messages.errorMsg = e.getMessage();
			logger.warn(Messages.errorMsg);
			return false;
		}
	}

	/*******************************************************************************
	Function Name 					: waitForJQueryProcessing
	Description						: Wait for jquery processing bar to process on screen with time out of 'iTimOut".
	Parameters						: iTimeout(Seconds)
	Usage							: bStatus = waitForJQueryProcessing(iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean waitForEnable(By objLocator,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyEnable(objLocator);
			if(bStatus)
			{
				logger.info("Element :"+objLocator + " is enabled");
				return true;
			}
		}
		Messages.errorMsg = "TimedOut due to element is not enabled after "+iTimeout +"secs";
		logger.warn(Messages.errorMsg);
		return false;
	}

	/*************************************************************************
	Function Name 					: waitForWindow
	Description						: Wait for the given window with time out of 'iTimOut".
	Parameters						: sWindowName, iTimeout(Seconds)
	Usage							: bStatus = waitForWindow(sWindowName, iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ***************************************************************************/
	public static boolean waitForWindow(String sWindowName, long iTimeout)
	{
		String sMainHandler = driver.getWindowHandle();
		System.out.println(sMainHandler);
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			Set<String> handlers = driver.getWindowHandles();
			System.out.println("size of windows "+handlers);
			Iterator<String> winIterator = handlers.iterator();
			while (winIterator.hasNext())
			{
				String BancsChildWin = winIterator.next();
				System.out.println(BancsChildWin);
				if(sWindowName.equalsIgnoreCase(BancsChildWin))
				{
					logger.info("Window :"+sWindowName + " is present");
					return true;
				}
			}
		}
		Messages.errorMsg = "TimedOut due to new window is not availble after "+iTimeout +"secs";
		logger.warn(Messages.errorMsg);
		return false;
	}


	public static boolean setQuickElementWait() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		return true;
	}

	public static boolean resetElementWait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return true;
	}

	/*************************************************************************
	Function Name 					: waitTillPageLoaded
	Description						: Wait untils the page gets loaded with time out of 'iTimOut".
	Parameters						: iTimeout(Seconds)
	Usage							: bStatus = waitTillPageLoaded(iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ***************************************************************************/
	public static boolean waitTillPageLoaded(Integer iTimeOut)
	{

		ExpectedCondition<Boolean> expectation = new  ExpectedCondition<Boolean>() 
		{
			public Boolean apply(WebDriver driver) 
			{
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
			wait.until(expectation);
		} 
		catch (Throwable error) 
		{
			Messages.errorMsg = error.toString();
			return false;
		}
		return true;
	}


	/*************************************************************************
	Function Name 					: waitForNoWebElement
	Description						: Waits for the given element not to present in the given time out of 'iTimOut".
	Parameters						: objLocator, iTimeout(Seconds)
	Usage							: bStatus = waitForNoWebElement(objLocator,iTimeout)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ***************************************************************************/
	public static boolean waitForNoWebElement(By objLocator, int iTimeOut) throws InterruptedException {

		Integer TIMEOUT = iTimeOut;
		boolean bValue = false;

		setQuickElementWait();
		for (int iSecond = 1;; iSecond++) {
			if (iSecond >= TIMEOUT) {
				// log a failure if the WebElement is still displayed after the timeout
				resetElementWait();
				Messages.errorMsg = "timeout : " + TIMEOUT	+ "  waiting for WebElement : " + objLocator + " - the webelement is still displayed";
				return bValue;
			}
			try {
				// check to see if the element is displayed
				if (waitForElementVisibility(objLocator, 2)) {
					waitForGivenTimeOut(1000);
					continue;
				}
				// if element is not present means, moto is achieved. return true
				else
					resetElementWait();
				return true;
			} 
			catch (Exception e) {
				resetElementWait();
				return true;
			}
		}
	}




	/*******************************************************************************
	Function Name 					: waitForElementToBeClickable
	Description						: Wait for the given element to be clickable
	Parameters						: objLocator, iTimeout(seconds)
	Usage							: bStatus = waitForElementVisibility(objLocator, iTimeout)
	Created By						: Kalpit Jadhav
	Created On						: 
	 ******************************************************************************/

	public static boolean waitForElementToBeClickable(By objLocator,long iTimeout)
	{
		try{
			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			wait2.until(ExpectedConditions.elementToBeClickable(objLocator));
			logger.info("element "+objLocator+" is clickable after waiting.");	
			return true;
		}
		catch(Exception e)
		{	
			Messages.errorMsg = e.getMessage();
			logger.warn("element "+objLocator+" is not clickable after waiting "+iTimeout+" secs.");
			return false;
		}
	}

}
