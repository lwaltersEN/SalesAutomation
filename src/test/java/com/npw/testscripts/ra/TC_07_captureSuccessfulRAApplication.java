package com.npw.testscripts.ra;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.npw.lib.RA.CommonFunc;
import com.npw.lib.RA.NavigateTo;
import com.npw.lib.RA.RA;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Messages;
import com.om.framework.lib.Utilities;
import com.om.framework.reporting.Reporting;

public class TC_07_captureSuccessfulRAApplication extends BaseTest {

	private static boolean bStatus;
	private static Map<String,String> objRADtls;
	private static String TestData_path_RA= "TestData\\RA_TestData2.xls";
	private static String sheetName="RA_Sheet";
	private static String TestCaseName="TC_07_captureSuccessfulRAApplication";
	
	@Test
	public static void captureSuccessfulApplication() throws IOException, HeadlessException, AWTException {
		
		//Initialize testcase for reporting purpose
		Reporting.Functionality = "RA";
		Reporting.Testcasename = TestCaseName;
		
		//Read data from excelsheet 
		objRADtls = Utilities.readTestData(TestData_path_RA,sheetName, TestCaseName);

		// Create application
		bStatus = RA.captureSuccessfulRAApplication(objRADtls);

		if (bStatus) {
			Reporting.logResults("Pass", "Create RA application", "Succesfully created RA Application with reference num: "+RA.sRAReferenceNum);
			return;
		}else {
			Reporting.logResults("Fail", "Create RA application", "Unable to create RA application.. due to.."+Messages.errorMsg);
			return;
		}
	}
	
}
