package com.npw.lib.RA;


import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.npw.locators.RA.Locators;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Elements;
import com.om.framework.lib.Messages;
import com.om.framework.lib.Verify;
import com.om.framework.lib.Wait;
import com.om.framework.reporting.Reporting;


public class Gap extends BaseTest {

	private static boolean bStatus;
	private static Logger logger=Logger.getLogger("Gap");
	private static int iSync=20;
	

	// Fill all details on the gap cover app and submit
	public static boolean createGapCoverApp(Map<String,String> objExcel) throws HeadlessException, IOException, AWTException {

		//Go to gap app 
		bStatus=Elements.clickElement(By.xpath(Locators.NavigationMenu.AppBuyNow("Old Mutual Gap Cover")));
		if (!bStatus) return bStatus;
		
		// Enter details in the leadtracker
		bStatus=LeadTracker(objExcel);
		if (!bStatus) return bStatus;

		// enter personal details
		bStatus=personalDetails(objExcel);
		if (!bStatus) return bStatus;

		// enter dependants details
		bStatus=dependantDetails();
		if (!bStatus) return bStatus;

		//enter medical aid details
		bStatus=medicalAidDetails();
		if (!bStatus) return bStatus;

		//enter payment details
		bStatus=paymentDetails();
		if (!bStatus) return bStatus;

		// confirm the information and submit the application
		bStatus=confirmation();
		if (!bStatus) return bStatus;

		return bStatus;
	}

	// Enter details in the leadtracker before reaching personal details page
	public static boolean LeadTracker(Map<String,String> objExcel) {
		List<WebElement> radioButonlist;

		try {
			//wait till page loads
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Keep the following handy")), iSync);
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;
			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("First thing’s first, tell us a bit about yourself")), iSync);
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("First Name(s)")), objExcel.get("FirstName"));
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Surname")), objExcel.get("LastName"));
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Cellphone Number")), objExcel.get("CellPhone"));
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Email Address")), objExcel.get("Email"));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("When do you want to start your cover?")), iSync);
			if (!bStatus) return bStatus;

			//check if the 2 radio buttons are present for "When do you want to start your cover?"
			radioButonlist=Elements.getWebElements(By.xpath(Locators.GapApp.radioBtn("")));
			if(radioButonlist.size()!=2) return bStatus;

			//navigate to personal details page 
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Personal details")), iSync);
			if (!bStatus) return bStatus;

			Reporting.logResults("Pass", "Enter lead tracker", "Succesfully entered data to leadtracker");


		} catch (Exception e) {
			Messages.errorMsg=e.getMessage();
			logger.warn("Not able to fill personal details page due to......"+Messages.errorMsg);
			return false;
		}
		return bStatus;

	}


	// enter personal details
	public static boolean personalDetails(Map<String,String> objExcel) {
		try {
			bStatus=Wait.waitTillPageLoaded(iSync);
			if (!bStatus) return bStatus;
			//click dropdown for title
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.clickDropDown("Mr")));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectDropDownValue(objExcel.get("Title"))));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Initials")),objExcel.get("Initials"));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Alternate Contact Number")),objExcel.get("AlternateNumber"));
			if (!bStatus) return bStatus;
			
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("RSA ID Number")),objExcel.get("RsaId"));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Date of Birth")),objExcel.get("DateOfBirth"));
			if (!bStatus) return bStatus;

			//navigate to personal details part 2 "Where do you live?"
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Postal Address")), iSync);
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Postal address")),objExcel.get("PostalAddress"));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Suburb")),objExcel.get("Suburb"));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("City")),objExcel.get("City"));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Postal Code")),objExcel.get("PostalCode"));
			if (!bStatus) return bStatus;

			//navigate to Dependant details
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Would you like to add any dependants?")), iSync);
			if (!bStatus) return bStatus;

			Reporting.logResults("Pass", "Enter data to personal details", "Succesfully entered data to personal details");

		} catch (Exception e) {
			Messages.errorMsg=e.getMessage();
			logger.warn("Not able to fill personal details page due to......"+Messages.errorMsg);
			return false;
		}

		return bStatus;
	}

	// enter dependants details
	public static boolean dependantDetails() {
		try {
			bStatus=Wait.waitTillPageLoaded(iSync);
			if (!bStatus) return bStatus;
			//Select Yes for dependants 
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.ShadowDom("Would you like to add any dependants")));
			if (!bStatus) return bStatus;
			CommonFunc.findShadowDom(By.xpath(Locators.GapApp.ShadowDom("Would you like to add any dependants"))).click();
			Thread.sleep(1000);
			//click dropdown for Relationship to you
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.clickDropDown("Spouse")));
			if (!bStatus) return bStatus;
			Thread.sleep(1000);
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.clickDropDown("Spouse")));
			if (!bStatus) return bStatus;
			
			//select dropdown value for Relationship to you
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectDropDownValue("Spouse")));
			if (!bStatus) return bStatus;
			
			//enter first name
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.inputText("First Name(s)")));
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("First Name(s)")), "jhgkljffjhg");
			if (!bStatus) return bStatus;
			
			//Enter Surname
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Surname")), "fdffdfg");
			if (!bStatus) return bStatus;
			
			//enter DOB
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Date of Birth")), "03032000");
			if (!bStatus) return bStatus;
			
			//click save dependant button
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("save dependant")));
			if (!bStatus) return bStatus;
			
			//navigate to Medical aid details
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;
			
			bStatus=Wait.waitTillPageLoaded(iSync);
			if (!bStatus) return bStatus;
			
			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Your medical aid details")), iSync);
			if (!bStatus) return bStatus;

			Reporting.logResults("Pass", "Enter data to dependants details", "Succesfully entered data to dependants details");


		} catch (Exception e) {
			// TODO Auto-generated catch block

		}



		return bStatus;
	}

	//enter medical aid details
	public static boolean medicalAidDetails() {
		try {
			
		//	CommonFunc.findShadowDom(By.xpath(Locators.GapApp.ShadowDom("Have you had Medical"))).click();
			
			bStatus=Wait.waitTillPageLoaded(iSync);
			if (!bStatus) return bStatus;
			
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Medical Aid Provider")), "kljljkljo");
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Medical Aid Number")), "78687686786");
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Medical Aid Plan")), "Testplan");
			if (!bStatus) return bStatus;
			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Medical Aid Start Date")), "02032009");
			if (!bStatus) return bStatus;
			//navigate to payment details
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Are you the account holder?")), iSync);
			if (!bStatus) return bStatus;

			Reporting.logResults("Pass", "Enter medical aid details", "Succesfully entered data to medical aid details");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Not able to fill details on medical aid details due to ...."+e.getMessage());
		}

		return bStatus;
	}
	//enter payment details
	public static boolean paymentDetails() throws HeadlessException, IOException, AWTException {
		try {
			//Thread.sleep(2000);
			Wait.waitTillPageLoaded(iSync);
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.clickDropDown("1st")));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.clickDropDown("1st")));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectDropDownValue("1st")));
			if (!bStatus) return bStatus;
			Thread.sleep(1000);
			bStatus=Wait.waitForElementToBeClickable(By.xpath(Locators.GapApp.clickDropDown("ABSA")), iSync);
			if (!bStatus) return bStatus;		
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.clickDropDown("ABSA")));
			if (!bStatus) return bStatus;
			
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.clickDropDown("ABSA")));
			if (!bStatus) return bStatus;
			Thread.sleep(2000);
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectDropDownValue("ABSA")));
			if (!bStatus) return bStatus;

			bStatus=Elements.enterText(By.xpath(Locators.GapApp.inputText("Account Number")), "897897897987");
			if (!bStatus) return bStatus;

			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.clickDropDown("Cheque")));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectDropDownValue("Cheque")));
			if (!bStatus) return bStatus;
			//navigate to confirmation
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Personal Details")), iSync);
			if (!bStatus) return bStatus;
			Reporting.logResults("Pass", "Enter payment details", "Succesfully entered data to payment details");
			
		} 
		
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Not able to fill details on payment details due to ...."+e.getMessage());
			return false;
		}

		return bStatus;
	}

	// confirm the information and submit the application
	public static boolean confirmation() {

		try {

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Continue")));
			if (!bStatus) return bStatus;

			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("What You Get")), iSync);
			if (!bStatus) return bStatus;

			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.GapApp.selectCheckBox("understand and accept")));
			if (!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.selectCheckBox("understand and accept")));
			if (!bStatus) return bStatus;
			//click submit
			bStatus=Elements.clickElement(By.xpath(Locators.GapApp.btnContinueOrPrevious("Submit")));
			if (!bStatus) return bStatus;
			//Confirm page is validated
			Reporting.logResults("Pass", "Validate Confirm page", "Succesfully validated confirm page");
			//increase waitime
			iSync=30;
			//wait till page navigates to next page
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.GapApp.subHeaderTitle("Thank you")), iSync);
			if (!bStatus) return bStatus;

			// read success message
			bStatus=Verify.verifyTextVisible(By.xpath(Locators.GapApp.getSuccessMsg()), "You have successfully submitted your application form for Old Mutual Gap Cover.");
			if (!bStatus) return bStatus;
			System.out.println("Gap Cover confirmation message is...."+ Elements.getText(By.xpath(Locators.GapApp.getSuccessMsg())));
		}
		catch(Exception e) {
			System.out.println("unable to fill details on comfirmation page due to...."+ e.getMessage());
		}
		return bStatus;
	}



	public static void getShadowElement() {


		try {
			
	/*		
			//WebElement root1=driver.findElement(By.xpath("//strong[contains(text(),'Have you had Medical')]//..//following-sibling::div[1]//om-toggle-switch"));
			WebElement root1=driver.findElement(By.cssSelector("om-page-bg-color.hydrated div.content-wrapper om-application-page.hydrated:nth-child(5) div.om-ra-application-page div.page-content-wrapper:nth-child(2) om-layout-with-sidebar.hydrated div.om-layout-with-sidebar section.content om-gap-dependent-details.hydrated div.gap-dependent-details div.form div.form-fields-wrapper div:nth-child(1) div.toggle-wrapper.large-gap:nth-child(6) > om-toggle-switch.hydrated"));
			WebElement shadowRoot1 = expandRootElement(root1);
			//div/label
			
			WebElement element=shadowRoot1.findElement(By.cssSelector("css=span.slider"));
			element.click();
			//System.out.println(str);

			
			  Shadow shadow = new Shadow(driver);
			  WebElement element = shadow.findElement("om-page-bg-color.hydrated div.content-wrapper om-application-page.hydrated:nth-child(5) div.om-ra-application-page div.page-content-wrapper:nth-child(2) om-layout-with-sidebar.hydrated div.om-layout-with-sidebar section.content om-gap-dependent-details.hydrated div.gap-dependent-details div.form div.form-fields-wrapper div:nth-child(1) div.toggle-wrapper.large-gap:nth-child(6) > om-toggle-switch.hydrated");
			  String text = element.getText();
*/
			
		}
		catch(Exception e) {

		}
		//return null;
	}
	

/*
	public static WebElement expandRootElement(WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return arguments[0].shadowRoot",element);
		return ele;
	}
	
	
	*/

	




}