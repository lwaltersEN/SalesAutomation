package com.npw.lib.RA;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Map;

import org.apache.bcel.verifier.structurals.ExceptionHandler;

import org.openqa.selenium.By;

import com.npw.locators.RA.Locators;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Elements;
import com.om.framework.lib.Messages;
import com.om.framework.lib.UserActions;
import com.om.framework.lib.Wait;

import com.om.framework.reporting.Reporting;
import org.apache.log4j.Logger;

public class NavigateTo extends BaseTest
{
	private static boolean bStatus;
	private static int iRAwait=25;
	private static Logger logger=Logger.getLogger("NavigateTo");


	public static boolean appMenu(String sAppMenuItem)
	{
		bStatus=Wait.waitForElementVisibility(By.xpath(Locators.NavigationMenu.getHeaderMenu(sAppMenuItem)), 15);
		bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.getHeaderMenu(sAppMenuItem)));

		return bStatus;
	}


	/*******************************************************************************
	Function Name 					: hoverAndClickHeaderMenu
	Description						: Go to Application landing page from header menu 
	Parameters						: Main header name, Sub header Name, App landing page name
	User info or parameter data		: List of possible values for 3 parameters,
										1) sHeader : Personal , Wealth, Business, Corporate, Claims, Careers, About
										2) sChildHeader : Home, Your Goals, Our Solutions, Rewards, Financial Education, Tools & Calculators
										3) 
											a) list of options available for Sub menu "Your Goals" : 
												Make the most of retirement
												Cover the cost of education
												Put money aside for a rainy day
												Have a big expense coming up
												Protect my family in case I die
												Protect me against the unexpected
												Protect the things that matter to me
											b) list of options available for Sub menu "Our Solutions" :
												Money Account
												Personal Loans
												Education Plans
												Retirement Plans
												Investment Plans
												Life & Disability
												Funeral
												Short Term Insurance
												Health
												Wills, Trusts & Estates
												Financial Planning
												Find an Adviser
												Broker Corner
												Private Wealth Management
												Fairbairn Consult
											C) list of options available for Sub menu "Financial Education" :
												MoneyVersity
												Smart Budgeting
												Articles
												On the Money

	Created By						: Kalpit Jadhav
	Created On						: 12 Jan 2020

	 ******************************************************************************/
	public static boolean hoverAndClickHeaderMenu(String sHeader, String sChildHeader, String sAppname) throws HeadlessException, IOException, AWTException
	{

		try {

			bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.getHeaderMenu(sHeader)));
			if(!bStatus) return bStatus;

			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.NavigationMenu.subHeaderHover(sChildHeader)), iRAwait);
			if(!bStatus) return bStatus;
			bStatus=UserActions.mouseOver(By.xpath(Locators.NavigationMenu.subHeaderHover(sChildHeader)));
			if(!bStatus) return bStatus;

			bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.subHeaderHover(sAppname)));
			if (bStatus==false) {
				Reporting.logResults("Fail", "Go to Application landing page from header menu", "Not able to navigate to "+sAppname+" from "+sChildHeader+" due to "+Messages.errorMsg+" ");
				return bStatus;
			}
		}
		catch(Exception e) {
			Reporting.logResults("Fail", "Go to Application landing page from header menu", "Not able to navigate to "+sAppname+" from "+sChildHeader+" due to "+Messages.errorMsg+" ");
			logger.warn("Cannot navigate to "+sAppname+" due to ...... "+e.getMessage());
			return false;
		}

		return bStatus;
	}

	public static boolean hoverAndClickHeaderMenu(Map<String,String> objMenuNames) throws HeadlessException, IOException, AWTException
	{
		return hoverAndClickHeaderMenu(objMenuNames.get("HeaderMenu"),objMenuNames.get("SubHeaderMenu"), objMenuNames.get("AppName"));
	}


	public static boolean hoverAndClickHeaderMenu(String sHeader, String sChildHeader) throws HeadlessException, IOException, AWTException
	{


		//bStatus=Wait.waitForElementVisibility(By.xpath(Locators.NavigationMenu.getHeaderMenu(sHeader)), iRAwait);
		bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.getHeaderMenu(sHeader)));
		if(!bStatus) return bStatus;




		bStatus=Wait.waitForElementVisibility(By.xpath(Locators.NavigationMenu.subHeaderHover(sChildHeader)), iRAwait);
		if(!bStatus) return bStatus;
		bStatus=UserActions.mouseOver(By.xpath(Locators.NavigationMenu.subHeaderHover(sChildHeader)));
		if(!bStatus) return bStatus;


		if (bStatus==false) {
			Reporting.logResults("Fail", "Go to Application landing page from header menu", "Not able to navigate to "+sChildHeader+" from "+sHeader+" due to "+Messages.errorMsg+" ");
			return bStatus;
		}

		return bStatus;
	}

	public static boolean hoverAndClickHeaderMenu(String sHeader) throws HeadlessException, IOException, AWTException
	{


		//bStatus=Wait.waitForElementVisibility(By.xpath(Locators.NavigationMenu.getHeaderMenu(sHeader)), iRAwait);
		bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.getHeaderMenu(sHeader)));
		if(!bStatus) return bStatus;

		if (bStatus==false) {
			Reporting.logResults("Fail", "Go to Application landing page from header menu", "Not able to navigate to "+sHeader+" due to "+Messages.errorMsg+" ");
			return bStatus;
		}

		return bStatus;
	}

}