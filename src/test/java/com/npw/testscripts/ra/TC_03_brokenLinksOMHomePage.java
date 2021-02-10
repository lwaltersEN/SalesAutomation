package com.npw.testscripts.ra;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import org.testng.annotations.Test;
import com.npw.lib.RA.CommonFunc;
import com.om.framework.basetest.BaseTest;
import com.om.framework.reporting.Reporting;

public class TC_03_brokenLinksOMHomePage extends BaseTest {
	private static boolean bStatus=false;
	private static String TestCaseName="TC_03_brokenLinksOMHomePage";

	@Test
	public static void homePageBrokenLinks() throws HeadlessException, IOException, AWTException, InterruptedException
	{
		//Initialize testcase for reporting purpose
		Reporting.Functionality = "HomePage";
		Reporting.Testcasename = TestCaseName;

		// Find all links on the home page and then find broken links
		bStatus=CommonFunc.findBrokenLinks();

		if (!bStatus) 
		{
			Reporting.logResults("Fail", "Check broken links on the home page", "Some of the links on the home page are not returning 200 status");
			return;
		}
		else {
			Reporting.logResults("Pass", "Check broken links on the home page", "All links are returning 200 on the home page");
			return;
		}
	}
}

