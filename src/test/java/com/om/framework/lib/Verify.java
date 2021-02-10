package com.om.framework.lib;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.om.framework.basetest.BaseTest;



public class Verify extends BaseTest
{
	
	private static boolean bStatus;
	public static boolean bVisibleCheck = true;
	private static Logger logger=Logger.getLogger("Verify");


	/*******************************************************************************
	Function Name 					: verifyElementVisible
	Description						: verifies whether the given element is visible or not
	Parameters						: objLocator
	Usage							: bStatus = verifyElementVisible(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 *******************************************************************************/
	public static boolean verifyElementVisible(By objLocator)
	{
		try
		{
			Wait.setQuickElementWait();
			if(bVisibleCheck)
			{
				bStatus = driver.findElement(objLocator).isDisplayed();
				if(!bStatus)
					Messages.errorMsg = "Element "+objLocator+ " is not visibile";

				logger.info("Element "+objLocator+"  visibility is :"+bStatus);
				Wait.resetElementWait();
				return bStatus;
			}
			else
			{
				logger.info("As the Visibility check is False, Just checking the presence of the element in DOM..");
				Wait.resetElementWait();
				return verifyElementPresent(objLocator);
			}
		}
		catch(Exception e)
		{
			Wait.resetElementWait();
			Messages.errorMsg =e.getMessage();
			logger.warn("Element "+objLocator+" is not visible.");
			return false;
		}
	}

	/*******************************************************************************
	Function Name 					: verifyElementPresent
	Description						: verifies the given element existence in DOM
	Parameters						: objLocator
	Usage							: bStatus = verifyElementPresent(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyElementPresent(By objLocator)
	{
		try
		{
			driver.findElement(objLocator);
			logger.info("Element "+objLocator+" is present in DOM");
			return true;
		}
		catch(Exception e)
		{
			Messages.errorMsg =e.getMessage() ;
			logger.warn("Element "+objLocator+" is not present in DOM because "+Messages.errorMsg);
			return false;
		}
	}
	
	
	/*******************************************************************************
	Function Name 					: verifyTextVisible
	Description						: verifies the given text is visible on screen
	Parameters						: 
	Usage							: bStatus = verifyTextVisible()
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyTextVisible(String sText)
	{

		bStatus = driver.getPageSource().contains(sText);
		if(bStatus)
		{
			logger.info("The Text "+sText+" is present in the current page ");
			return true;
		}
		else
		{
			Messages.errorMsg = sText+" not found in the current page";
			logger.warn(Messages.errorMsg);
			return false;
		}
	}

	/*******************************************************************************
	Function Name 					: verifyTextVisible
	Description						: verifies the given text in the given element is visible on screen
	Parameters						: objLocator,sText
	Usage							: bStatus = verifyTextVisible(objLocator,sText)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyTextVisible(By objLocator, String sText)
	{
		bStatus = verifyElementVisible(objLocator);
		if(!bStatus)
		{
			logger.warn("The Text "+sText+" is not present in the element "+objLocator+" because "+Messages.errorMsg);
			return false;
		}
		bStatus = driver.findElement(objLocator).getText().contains(sText);
		if(bStatus)
		{
			logger.info("The Text "+sText+" is present in the element "+objLocator+" because "+Messages.errorMsg);
			return true;
		}
		Messages.errorMsg = sText+" not found in the locator "+objLocator;
		logger.warn(Messages.errorMsg);
		return false;
	}

	/*******************************************************************************
	Function Name 					: verifyChecked
	Description						: verifies the given element is checked
	Parameters						: objLocator
	Usage							: bStatus = verifyTextVisible(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyChecked(By objLocator)
	{
		bStatus = verifyElementVisible(objLocator);
		if(!bStatus)
		{
			logger.warn("The check box has cannot be checked because "+Messages.errorMsg);
			return false;
		}
		bStatus = driver.findElement(objLocator).isSelected();
		if(bStatus)
		{
			logger.info("The check box has already been selected");
			return true;
		}
		Messages.errorMsg = objLocator+" is not selected";
		logger.warn(Messages.errorMsg);		
		return false;

	}

	/*******************************************************************************
	Function Name 					: verifyFileExists
	Description						: verifies the given file exist
	Parameters						: objLocator
	Usage							: bStatus = verifyFileExists(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyFileExists(String sFileName)
	{		
		File objFile = new File(sFileName);
		if (objFile.exists())
		{
			logger.info(sFileName+ "exist in directory");
			return true;
		}
		Messages.errorMsg = sFileName+ " doesn't exist in directory";
		logger.warn(Messages.errorMsg);
		return false;
	}

	/*******************************************************************************
	Function Name 					: verifyItemPresentInDropDown
	Description						: verifies the given item present in the given select drop down
	Parameters						: objLocator, sItem
	Usage							: bStatus = verifyItemPresentInDropDown(objLocator, sItem)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyItemPresentInDropDown(By objLocator,String sItem)
	{
		bStatus = verifyElementVisible(objLocator);
		if(!bStatus)
		{
			logger.warn(sItem+" cannot be verified for the locator "+objLocator+" because "+Messages.errorMsg);
			return false;
		}

		try
		{
			Select select = new Select(Elements.getWebElement(objLocator));
			List<WebElement> element = select.getOptions();
			for (int iCount = 0; iCount < element.size(); iCount++) 
			{
				if(element.get(iCount).getText().equalsIgnoreCase(sItem))
				{
					logger.info(sItem+" option is present in the element "+objLocator);
					return true;
				}
			}
			Messages.errorMsg = sItem+" option not found in the element "+objLocator;
			logger.warn(Messages.errorMsg);
			return false;
		}
		catch(Exception e)
		{
			Messages.errorMsg = e.getMessage();
			logger.warn(sItem+" item cannot be found because "+Messages.errorMsg);
			return false;
		}
	}

	/*******************************************************************************
	Function Name 					: verifyEnable
	Description						: verifies the given item is enabled
	Parameters						: objLocator
	Usage							: bStatus = verifyEnable(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyEnable(By objLocator)
	{
		bStatus = verifyElementPresent(objLocator);
		if(!bStatus)
		{
			logger.warn("Element "+objLocator+" is not visible "+Messages.errorMsg);
			return false;
		}
		bStatus = driver.findElement(objLocator).isEnabled();
		if(bStatus)
		{
			logger.info("The element is enabled");
			return true;
		}
		Messages.errorMsg = objLocator+" is not enabled";
		logger.warn(Messages.errorMsg);
		return false;
	}

	/*******************************************************************************
	Function Name 					: verifyAlertPresent
	Description						: verifies if any alert is present
	Parameters						: 
	Usage							: bStatus = verifyAlertPresent()
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static boolean verifyAlertPresent()
	{
		String alertMsg = Alerts.getAlertMessage();
		if(alertMsg == null)
		{
			logger.warn("No alert found");
			return false;
		}
		logger.info("Alert present");
		return true;
	}

	/*******************************************************************************
	Function Name 					: verifyElementsPresent
	Description						: verifies the matching elements to the given element(objLocator) are present in DOM
	Parameters						: objLocator
	Usage							: bStatus = verifyElementsPresent(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static Boolean verifyElementsPresent(By objLocator) 
	{
		Wait.setQuickElementWait();
		try 
		{
			driver.findElements(objLocator);
			logger.info("The elements are present in the DOM");
			Wait.resetElementWait();
			return true;
		} catch (NoSuchElementException e) 
		{
			Messages.errorMsg = e.getMessage();
			logger.warn("The elements are prsent in the DOM because "+Messages.errorMsg);
			Wait.resetElementWait();
			return false;
		}
	}

}
