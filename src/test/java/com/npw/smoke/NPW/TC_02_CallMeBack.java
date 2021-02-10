package com.npw.smoke.NPW;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;

import org.testng.annotations.Test;

import com.npw.lib.RA.CommonFunc;
import com.npw.lib.RA.RA;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Messages;
import com.om.framework.reporting.Reporting;

public class TC_02_CallMeBack extends BaseTest {
	
	private static boolean bStatus;
	private static String TestCaseName="TC_02_CallMeBack";
	
	@Test
	public static void CheckCallMeBack() throws HeadlessException, IOException, AWTException, InterruptedException
	{
		
		Reporting.Functionality = "NPW";
		Reporting.Testcasename = TestCaseName;
		System.out.println("Call me Back");
		
		bStatus=CommonFunc.callmeback();
		//if(!bStatus) return;
		
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
