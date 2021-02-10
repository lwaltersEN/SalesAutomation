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


public class TC_02_errorMsgValidationLeadTrackerRAApplication extends BaseTest {
	private static boolean bStatus;
	private static Map<String,String> objRADtls;
	private static Map<String,String> objRAError;
	private static String TestData_path_RA= "TestData\\RA_TestData.xls";
	private static String sheetName="RA_Sheet";
	private static String sheetName2="ErrorMessagesValidation";
	private static String TestCaseName="TC_02_invalidMobileNumberRAApplication";

	@Test
	public static void errorMsgLeadtrackerRA() throws HeadlessException, IOException, AWTException
	{
		//Initialize testcase for reporting purpose
		Reporting.Functionality = "RA";
		Reporting.Testcasename = TestCaseName;
		
		//read data from Excel sheet
		objRADtls = Utilities.readTestData(TestData_path_RA,sheetName, TestCaseName);
		objRAError = Utilities.readTestData(TestData_path_RA,sheetName2, TestCaseName);

		//Navigation to RA Application page
		bStatus=NavigateTo.hoverAndClickHeaderMenu(objRADtls);
		if(!bStatus) return;

		//Select Retirement annuity plan
		bStatus=CommonFunc.selectPlan(objRADtls);
		if(!bStatus) return;

		// Enter Invalid data in leadtracker of the RA and verify all error messages on that page
		bStatus = RA.verifyInvalidDataLeadTrckr(objRADtls,objRAError);
		if (!bStatus) 
		{
			Reporting.logResults("Fail", "Fill the Lead tracker with invalid data  and validate error messages", "Unable to validate error mesage for .. due to.."+Messages.errorMsg);
			return;
		}
		Reporting.logResults("Pass", "Fill the Lead tracker with invalid data  and validate error messages", "First name, Surname, email and Cellphone number fields have correct error messages......."+ Messages.errorMsg);
	}
}

