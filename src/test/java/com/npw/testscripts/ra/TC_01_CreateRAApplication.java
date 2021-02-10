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


public class TC_01_CreateRAApplication extends BaseTest {
	private static boolean bStatus;
	private static Map<String,String> objRADtls;
	private static String TestData_path_RA= "TestData\\RA_TestData.xls";
	private static String sheetName="RA_Sheet";
	private static String TestCaseName="TC_01_CreateRAApplication";


	@Test
	public static void createApp() throws HeadlessException, IOException, AWTException{
		
		//Initialize testcase for reporting purpose
		Reporting.Functionality = "RA";
		Reporting.Testcasename = TestCaseName;

		//Read data from excelsheet 
		objRADtls = Utilities.readTestData(TestData_path_RA,sheetName, TestCaseName);

		//Navigation to RA Application page
		bStatus=NavigateTo.hoverAndClickHeaderMenu(objRADtls);
		if(!bStatus) return;

		//Select Retirement annuity plan
		bStatus=CommonFunc.selectPlan(objRADtls);
		if(!bStatus) return;

		// Create application
		bStatus = RA.createRAApplication(objRADtls);

		if (bStatus) {
			Reporting.logResults("Pass", "Create RA application", "Succesfully created RA Application with reference num: "+RA.sRAReferenceNum);
			return;
		}
		else {
			Reporting.logResults("Fail", "Create RA application", "Unable to create RA application.. due to.."+Messages.errorMsg);
			return;
		}
	}
}

