package com.npw.smoke.NPW;


import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.npw.lib.RA.CommonFunc;

import com.npw.locators.RA.Locators;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Elements;

import com.om.framework.lib.Utilities;
import com.om.framework.reporting.Reporting;


public class TC_01_CheckHomePage extends BaseTest {
	
	private static boolean bStatus;
	private static String TestCaseName="TC_01_CheckHomePage";
	private static Map<String,String> objRADtls;
	private static String sheetName="Smoke_Sheet";
	private static String TestData_path_NWP= "TestData\\NPWSmoke_TestData.xls";
	
	@Test
	public static void ChechPage() throws HeadlessException, IOException, AWTException, InterruptedException
	{
		Reporting.Functionality = "NPW";
		Reporting.Testcasename = TestCaseName;
		System.out.println("HomePAge");
		
		objRADtls = Utilities.readTestData(TestData_path_NWP,sheetName, TestCaseName);
		
		if(objRADtls.get("Personal").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Personal")));
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
			
		}
		
		if(objRADtls.get("Wealth").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Wealth")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
		}
		
		if(objRADtls.get("Business").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Business")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
	
			
		}
		
		if(objRADtls.get("Corporate").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Corporate")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
			
		}
		
//		if(objRADtls.get("Institutions").equalsIgnoreCase("Yes"))
//		{	
//			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Institutions")));
//			if(!bStatus) return;
//			bStatus =CommonFunc.checkheadermenu();
//			if(!bStatus) return;
//			
//			bStatus =CommonFunc.checkfootermenu();
//			
//			
//		}
		
		if(objRADtls.get("Claims").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Claims")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
		}
		
		if(objRADtls.get("Careers").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("Careers")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
			
		}
		
		if(objRADtls.get("About").equalsIgnoreCase("Yes"))
		{	
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.HeaderMenu("About")));
			if(!bStatus) return;
			bStatus =CommonFunc.checkheadermenu();
			if(!bStatus) return;
			
			bStatus =CommonFunc.checkfootermenu();
			
			
		}
		
		if (bStatus) {
			Reporting.logResults("Pass", "Check Home Page Footer Header", "Succesfully displayed Home Page Footer Header");
			return;
		}
		else {
			Reporting.logResults("Fail", "Check Home Page Footer Header", "Unable to find Home Page Footer or Header ");
			return;
		}
		
		
		
		
	}
	

}
