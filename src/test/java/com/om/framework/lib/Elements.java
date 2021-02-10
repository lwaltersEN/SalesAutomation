package com.om.framework.lib;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.npw.lib.RA.CommonFunc;
import com.om.framework.basetest.BaseTest;

public class Elements extends BaseTest 
{

	private static boolean bStatus;
	private static int iWait=20;
	private static Logger logger=Logger.getLogger("Elements");
	

	
	/*******************************************************************************
	Function Name 					: getWebElement
	Description						: Returns the web element of the given element locator
	Parameters						: (By objLocator)
	Usage							: Webelement ele = getWebElement(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ********************************************************************************/
	public static WebElement getWebElement(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			logger.info("The Element "+objLocator+" is visible and can be used");
			return driver.findElement(objLocator);
		}
		logger.warn("The Element "+objLocator+" is not visible and cannot be used");
		return null;
	}
	
	
	/*******************************************************************************
	Function Name 					: getWebElements
	Description						: Returns the list of web elements of the given element locator
	Parameters						: (By objLocator)
	Usage							: Webelement ele = getWebElements(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static List<WebElement> getWebElements(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			logger.info("The Elements with locator "+objLocator+" is visible and can be used");
			return driver.findElements(objLocator);
		}
		logger.warn("The Element with locator "+objLocator+" is not visible and cannot be used");
		return null;
	}


	/*******************************************************************************
	Function Name 					: getXpathCount
	Description						: Returns the xpath count of the given xpath
	Parameters						: (By objLocator)
	Usage							: iCount = getXpathCount(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static int getXpathCount(By objLocator)
	{
		int iSize = 0;
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			iSize = driver.findElements(objLocator).size();
			logger.info("The xpath count of the element "+objLocator+" is "+iSize);
			return iSize;
		}
		logger.warn("The xpath count of the element "+objLocator+" is "+iSize+" because "+Messages.errorMsg);
		return iSize;	
	}

	/*******************************************************************************
	Function Name 					: enterText
	Description						: Enters the given text in the specified textbox
	Parameters						: objLocator, sValue
	Usage							: bStatus = enterText(objLocator, sValue)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 * @throws InterruptedException 
 ******************************************************************************/
	public static boolean enterText(By objLocator,String sValue) throws InterruptedException
	{
		bStatus = Wait.waitForElementVisibility(objLocator, iWait);
		if(bStatus)
		{
			CommonFunc.scrollToViewElement(objLocator);
			driver.findElement(objLocator).click();
			driver.findElement(objLocator).clear();
			driver.findElement(objLocator).sendKeys(sValue);
			logger.info("The text "+sValue+" has been inputted successfully.");
			return true;
		}
		logger.warn("The text "+sValue+" could not be entered successfully");
		return false;	
	}
	
	/*******************************************************************************
	Function Name 					: enterText
	Description						: Enters the given key against the given element
	Parameters						: objLocator, sValue
	Usage							: bStatus = enterText(objLocator, sValue)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	
	public static boolean enterText(By objLocator,Keys sKey)
	{
		bStatus = Wait.waitForElementVisibility(objLocator, 20);
		if(bStatus)
		{
			driver.findElement(objLocator).sendKeys(sKey);
			logger.info("The key "+sKey+" has been pressed against the locator " + objLocator + " successfully.");
			return true;
		}
		logger.warn("The key "+sKey+" has been pressed against the locator " + objLocator + " successfully.");
		return false;	
	}


	
	/*******************************************************************************
	Function Name 					: selectCheckbox
	Description						: Selects the required checkbox
	Parameters						: objLocator
	Usage							: bStatus = selectCheckbox(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean selectCheckbox(By objLocator) throws InterruptedException
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			bStatus = driver.findElement(objLocator).isSelected();
			if(!bStatus)
			{
				driver.findElement(objLocator).click();
				logger.info("The check box has been selected successfully");
			}
			logger.info("The check box has been already selected");	
			Wait.waitForGivenTimeOut(10);
			return true;
		}
		logger.warn("Cannot check the CheckBox because "+Messages.errorMsg);
		return false;
	}

	
	/*******************************************************************************
	Function Name 					: unSelectCheckbox
	Description						: un Selects the required checkbox
	Parameters						: objLocator
	Usage							: bStatus = unSelectCheckbox(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/

	public static boolean unSelectCheckbox(By objLocator) throws InterruptedException
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			bStatus = driver.findElement(objLocator).isSelected();
			if(bStatus)
			{
				driver.findElement(objLocator).click();
				logger.info("The check box has been unselected successfully");
			}
			logger.info("The check box has been already unselected");	
			Wait.waitForGivenTimeOut(10);
			return true;
		}
		logger.warn("Cannot uncheck the CheckBox because "+Messages.errorMsg);
		return false;
		
	}
	
	/*******************************************************************************
	Function Name 					: clickElement
	Description						: Clicks the element identified by `objLocator`
	Parameters						: objLocator
	Usage							: bStatus = clickButton(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean clickElement(By objLocator)
	{
		//bStatus = Verify.verifyElementVisible(objLocator);
		bStatus=Wait.waitForElementVisibility(objLocator, iWait);
		if(bStatus)
		{
			driver.findElement(objLocator).click();
			logger.info("The element "+objLocator+" has been clicked successfully");
			return true;
		}
		logger.warn("The element "+objLocator+" cannot be clicked due to: "+Messages.errorMsg);
		return false;
	}
	
	/*******************************************************************************
	Function Name 					: clearText
	Description						: clears the text in the element identified by `objLocator`
	Parameters						: objLocator
	Usage							: bStatus = clearText(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean clearText(By objLocator){
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			driver.findElement(objLocator).clear();
			logger.info("The text has been cleared from the input box "+objLocator+" successfully");
			return true;
		}
		logger.warn("The text could not be cleared from the input box "+objLocator);
		return false;
	}

	/*******************************************************************************
	Function Name 					: selectOptionByIndex
	Description						: Select the drop down value based on the given 'iIndexVal' and in the element identified by `objLocator`
	Parameters						: objLocator, IndexVal
	Usage							: bStatus = selectOptionByIndex(objLocator, iIndexVal)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean selectOptionByIndex(By objLocator, int iIndexVal)
	{
		WebElement wbElement = getWebElement(objLocator);
		if(wbElement != null)
		{
			try{
				Select sel = new Select(wbElement);
				sel.selectByIndex(iIndexVal);
				logger.info("The option from the dropdown has been selected by index "+iIndexVal+" successfully");
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				logger.warn("The option from the dropdown could not be selected by index "+iIndexVal+ " due to "+Messages.errorMsg);
				return false;
			}
		}
		return false;
	}

	/*******************************************************************************
	Function Name 					: selectOptionByValue
	Description						: Select the drop down value based on the given 'sValue' and in the element identified by `objLocator`
	Parameters						: objLocator, sValue
	Usage							: bStatus = selectOptionByValue(objLocator, sValue)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean selectOptionByValue(By objLocator,String sValue)
	{
		WebElement wbElement = getWebElement(objLocator);
		if(wbElement != null)
		{
			try{
				Select sel = new Select(wbElement);
				sel.selectByValue(sValue);
				logger.info("The option from the dropdown has been selected by value "+sValue+" successfully");
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				logger.warn("The option from the dropdown could not be selected by value "+sValue+ " due to "+Messages.errorMsg);
				return false;
			}
		}
		logger.warn("The option from the dropdown could not be selected by value "+sValue+ " due to "+Messages.errorMsg);
		return false;
	}
	
	/*******************************************************************************
	Function Name 					: selectOptionByVisibleText
	Description						: Select the drop down visible text based on the given 'sVisibleText' and in the element identified by `objLocator`
	Parameters						: objLocator, sValue
	Usage							: bStatus = selectOptionByVisibleText(objLocator, sVisibleText)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static boolean selectOptionByVisibleText(By objLocator,String sVisibleText)
	{
		WebElement wbElement = getWebElement(objLocator);
		if(wbElement != null)
		{
			try{
				Select sel = new Select(wbElement);
				sel.selectByVisibleText(sVisibleText);
				logger.info("The option from the dropdown has been selected by visible text "+sVisibleText+" successfully");
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				logger.warn("The option from the dropdown could not be selected by visible text "+sVisibleText+ " due to "+Messages.errorMsg);
				return false;
			}
		}
		logger.warn("The option from the dropdown could not be selected by visible text "+sVisibleText+ " due to "+Messages.errorMsg);
		return false;
	}

	
	/*******************************************************************************
	Function Name 					: getElementAttribute
	Description						: Reruns the given attribute value of the element identified by objLocator.
	Parameters						: objLocator, sAttrVal
	Usage							: bStatus = getElementAttribute(objLocator, sAttrVal)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static String getElementAttribute(By objLocator, String sAttrVal)
	{
		String sValue = null;
		bStatus = Verify.verifyElementPresent(objLocator);
		if(bStatus)
		{
			sValue = driver.findElement(objLocator).getAttribute(sAttrVal);
			if(sValue == null)
			{
				Messages.errorMsg = "The element "+objLocator+" has no attribute "+sAttrVal;
				logger.warn(Messages.errorMsg );
				return sValue;
			}
			logger.warn( "The element "+objLocator+" has value '"+sValue +"' for attribute "+sAttrVal);
			return sValue;
		}
		logger.warn( "The attribute "+sAttrVal+" of element "+objLocator+" value cannot be retrieved because "+Messages.errorMsg);
		return sValue ;
	}

	/*******************************************************************************
	Function Name 					: getText
	Description						: Reruns the text from the element identified by objLocator.
	Parameters						: objLocator
	Usage							: bStatus = getText(objLocator)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
 ******************************************************************************/
	public static String getText(By objLocator)
	{
		String sValue = null;
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			sValue = driver.findElement(objLocator).getText();
			if(sValue == null)
			{
				logger.info("The element "+objLocator+" has no text ");
				return sValue;
			}
			logger.warn("The text "+sValue+" from the element "+objLocator+" is retrieved");
			return sValue;	
		}
		logger.warn("The text from the element "+objLocator+" cannot be retrieved because "+Messages.errorMsg);
		return sValue;
	}
	
	/**
	 * Returns the value attribute of a given Web Element.
	 * 
	 * @param objLocator		By Locator
	 * @return String
	 */
	public static String getValue(By objLocator)
	{
		String sValue = null;
		try {
			sValue = getElementAttribute(objLocator, "value");
			logger.info("The element "+objLocator+" has a value of: " + sValue);
		}
		catch (Exception e) {
			logger.warn("The value from the element "+objLocator+" cannot be retrieved because "+Messages.errorMsg);
		}
		return sValue;
	}
	
	
	
	
	
	public static boolean checkWebElement(By objLocator)
    {
           bStatus=Wait.waitForElementVisibility(objLocator, iWait);
           if(bStatus)
           {
                  logger.info("The Element "+objLocator+" is visible");
                  return bStatus;
           }
           logger.warn("The Element "+objLocator+" is not visible and cannot be used");
           return bStatus;
    }
    
    public static boolean enterTextDE(By objLocator,String sValue) throws InterruptedException
    {
           bStatus = Wait.waitForElementVisibility(objLocator, iWait);
           if(bStatus)
           {
                  CommonFunc.scrollToViewElement(objLocator);
                  //driver.findElement(objLocator).click();
                  //driver.findElement(objLocator).clear();
                  driver.findElement(objLocator).sendKeys(sValue);
                  Thread.sleep(1000);
                  driver.findElement(objLocator).sendKeys(Keys.DOWN);
                  driver.findElement(objLocator).sendKeys(Keys.ENTER);
                  
                  logger.info("The text "+sValue+" has been inputted successfully.");
                  return true;
           }
           logger.warn("The text "+sValue+" could not be entered successfully");
           return false; 
    }
    
    
    public static boolean movetoclickElement(By objLocator) throws InterruptedException
    {
           //bStatus = Verify.verifyElementVisible(objLocator);
           bStatus=Wait.waitForElementVisibility(objLocator, iWait);
           if(bStatus)
           {
                  
                  Actions action = new Actions(driver);
                  action.moveToElement(driver.findElement(objLocator)).click().perform();
                  
                  logger.info("The element "+objLocator+" has been clicked successfully");
                  return true;
           }
           logger.warn("The element "+objLocator+" cannot be clicked due to: "+Messages.errorMsg);
           return false;
    }

	

}
