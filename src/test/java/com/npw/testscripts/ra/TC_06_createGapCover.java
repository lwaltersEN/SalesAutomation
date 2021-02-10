package com.npw.testscripts.ra;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;
import com.npw.lib.RA.Gap;
import com.npw.lib.RA.NavigateTo;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Utilities;
import com.om.framework.reporting.Reporting;

public class TC_06_createGapCover extends BaseTest {

	private static boolean bStatus=false;
	private static String TestCaseName="TC_06_createGapCover";
	private static Map<String,String> objExcel;
	private static String sFilepath="TestData\\GAP_TestData.xls";
	private static String sSheetName="Gap_Sheet";

	@Test
	public static void createGapCoverApp() throws HeadlessException, IOException, AWTException {
		
		Reporting.Functionality = "Gap";
		Reporting.Testcasename = TestCaseName;
		
		//Read test data from Excel sheet
		objExcel=Utilities.readTestData(sFilepath, sSheetName, TestCaseName);
		
		//Navigation to Gap Application page
		bStatus=NavigateTo.hoverAndClickHeaderMenu(objExcel);
		if(!bStatus) return;
		
		//Create GAP application
		bStatus=Gap.createGapCoverApp(objExcel);
		if (!bStatus) 
			Reporting.logResults("Fail", "Submit Gap Application to Call center", "Not able to submit Gap application");
		else
			Reporting.logResults("Pass", "Submit Gap Application to Call center", "Successfully submitted GAP application to call center");
	}



}
