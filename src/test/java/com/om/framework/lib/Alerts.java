package com.om.framework.lib;

import org.apache.log4j.Logger;

import com.om.framework.basetest.BaseTest;

public class Alerts extends BaseTest 
{

	private static boolean bStatus;
	private static Logger logger=Logger.getLogger("Alerts");
	
	/**
	 * Returns the alert message if any
	 * 
	 * @return String
	 * @author akosaraju
	 */
	public static String getAlertMessage()
	{
		String sAlertMsg=null;
		bStatus = Wait.waitForAlert(5);
		if(bStatus)
		{
			sAlertMsg = driver.switchTo().alert().getText();
			
			if(sAlertMsg != null)
			{
				logger.info("The text '"+sAlertMsg+"' from the alert has been retrieved successfully");
				return sAlertMsg;	
			}
			else
			{
				Messages.errorMsg = "There is no text present in the alert";
				logger.warn("There is no text present in the alert");
				return sAlertMsg;
			}
		}
		return  sAlertMsg;
	}

	/**
	 * Accepts the alert
	 * 
	 * @return boolean
	 * @author akosaraju
	 */
	public static boolean acceptAlert()
	{
		bStatus = Verify.verifyAlertPresent();
		if(bStatus)
		{
			driver.switchTo().alert().accept();
			return true;
		}
		return false;
	}

	/**
	 * Closes the alert
	 * 
	 * @return boolean
	 * @author akosaraju
	 */
	public static boolean closeAlert()
	{
		bStatus = Verify.verifyAlertPresent();
		if(bStatus)
		{
			driver.switchTo().alert().dismiss();
			logger.info("The alert has been dismissed successfully");
			return true;
		}
		return false;
	}
	
}
