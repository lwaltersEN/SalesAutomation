package com.npw.testscripts.ra;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.npw.lib.RA.NavigateTo;
import com.om.framework.lib.Utilities;
import com.om.framework.reporting.Reporting;

public class TC_09_captureRAApplicationWithLinkedInflationAndAdventurousInvestor {

	private static boolean bStatus;
	private static Map<String,String> objRADtls;
	private static String TestData_path_RA= "TestData\\RA_TestData.xls";
	private static String sheetName="RA_Sheet";
	private static String TestCaseName="TC_08_captureRAApplicationWithlinkedInflationAndComfortableInvestor";
	
	@Test
	public static void captureRAApplicationWithLinkedInflationAndAdventurousInvestor () throws IOException, HeadlessException, AWTException {
		
		//Initialize testcase for reporting purpose
		Reporting.Functionality = "RA";
		Reporting.Testcasename = TestCaseName;
		
		//Read data from excelsheet 
		objRADtls = Utilities.readTestData(TestData_path_RA,sheetName, TestCaseName);
		
		//Navigation to RA Application page
		bStatus=NavigateTo.hoverAndClickHeaderMenu(objRADtls);
		if(!bStatus) return;
	}
}
