package com.npw.lib.RA;
import java.util.Map;
import org.openqa.selenium.By;

import com.npw.locators.RA.Locators;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Elements;
import com.om.framework.lib.Messages;
import com.om.framework.lib.Verify;
import com.om.framework.lib.Wait;
import com.om.framework.reporting.Reporting;




public class RA extends BaseTest
{
	private static boolean bStatus=true;
	private static int iSync=20;
	public static String sRAReferenceNum;

	public static boolean createRAApplication(Map<String,String> objRADtls)
	{

		try
		{

			//Step 1 : fill LeadTracker
			bStatus = enterLeadTrackerDtls(objRADtls);
			if(!bStatus) return bStatus;
			//Step 2 : Enter investment details page
			bStatus= enterInvestmentDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 3 : Split your investment
			bStatus=splitYourInvestment();
			if(!bStatus) return bStatus;
			//Step 4 : Personal details
			bStatus=personalDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 5 : Additional details
			bStatus=additionalDetails();
			if(!bStatus) return bStatus;
			//Step 6 : beneficiaryPage
			bStatus=beneficiaryPage();
			if(!bStatus) return bStatus;
			//Step 7 Final checks
			bStatus=finalChecks(objRADtls);
			if(!bStatus) return bStatus;
			//Step 8 paymentDetails
			bStatus=paymentDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 9 paymentDetails
			bStatus=confirmationPage();
			if(!bStatus) return bStatus;

			sRAReferenceNum = "";

		}
		catch(Exception e)
		{

		}

		return bStatus;
	}
	
	public static boolean captureSuccessfulRAApplication(Map<String,String> objRADtls)
	{

		try
		{
			
			//Step 1 : retirement Annuity application page.
			//bStatus= retirementAnnuityApplicationPage(objRADtls);
			//if(!bStatus) return bStatus;
			//Step 2 : Enter investment details page
			bStatus= enterInvestmentSetupDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 4 : Personal details
			bStatus=enterPersonalDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 5 : beneficiaryPage
			bStatus=enterBeneficiaryDetailsPage(objRADtls);
			if(!bStatus) return bStatus;
			//Step 6 paymentDetails
			bStatus=enterPaymentDetails(objRADtls);
			if(!bStatus) return bStatus;
			//Step 7 confirmation page final checks
			bStatus=confirmationPageFinalChecks();
			if(!bStatus) return bStatus;

			sRAReferenceNum = "";

		}
		catch(Exception e)
		{

		}

		return bStatus;
	}



	public static boolean verifyInvalidDataLeadTrckr(Map<String,String>objInvalidNumData, Map<String,String>objInvalidNumError)
	{

		try
		{
			bStatus=enterLeadTrackerDtls(objInvalidNumData);
			if(!bStatus)
			{	
				bStatus=validateFieldErrorMsg(objInvalidNumError);
				if(!bStatus) return bStatus;
			}
		}

		catch (Exception e) {

		}
		return bStatus;
	}
	
	

	/*******************************************************************************
	Function Name 					: validateFieldErrorMsg
	Description						: This  is generic method to validate all error messages on the RA app 
	Parameters						: Hash map read from excelsheet for expected error messages against fields
	User info or parameter data		:
										
	Created By						: Kalpit Jadhav
	Created On						: 16 Jan 2020

	 ******************************************************************************/
	
	public static boolean validateFieldErrorMsg(Map<String,String> objFieldsNErrors)
	{
		boolean status=true;
		String sObservations = "";

		String[] arrFields = objFieldsNErrors.get("FieldsToBeValidated").split(",");
		String[] arrErrorMsgs = objFieldsNErrors.get("ErrorMessages").split(",");

		for(int i=0;i<arrFields.length;i++)
		{
			
			bStatus = validateFieldErrorMsg(arrFields[i].trim(), arrErrorMsgs[i].trim());
			if(bStatus)
				sObservations = sObservations + "\n" + "Validation is succesful for the field :"+ arrFields[i] + " against the exp error message :"+arrErrorMsgs[i];
			else
			{
				status=false;
				sObservations = sObservations + "\n" + Messages.errorMsg +"\n";
			}
		}

		Messages.errorMsg = sObservations;
		return status;
	}

	// this method is generic across RA app for validating expected error message against actual one
	public static boolean validateFieldErrorMsg(String sFieldName, String sExpErrorMsg)
	{

		Messages.errorMsg = "";

		String sValidationErrorXpath = Locators.RaApp.inputText(sFieldName)+"/following-sibling::span[@class='validation-error-text']";

		String sActualErrorMsg = Elements.getText(By.xpath(sValidationErrorXpath));

		if(sActualErrorMsg.contains(sExpErrorMsg)) return true;
		else
		{
			Messages.errorMsg = "Expected error message is:"+sExpErrorMsg +" but actual error displayed is: "+sActualErrorMsg +".....due to"+Messages.errorMsg ;
			return false;
		}
	}

	public static boolean enterLeadTrackerDtls(Map<String, String> objLeadTrackerDtls)
	{
		try
		{
			//Wait for page to load
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.clickDropDown(objLeadTrackerDtls.get("Title"))), iSync);
			//Select Title
			if(!bStatus) return bStatus;
			bStatus=CommonFunc.selectDropDown(objLeadTrackerDtls.get("Title"));
			if(!bStatus) return bStatus;
			//enter first name
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("first-name")), objLeadTrackerDtls.get("FirstName"));
			if(!bStatus) return bStatus;
			//enter surname name
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("surname")), objLeadTrackerDtls.get("LastName"));
			if(!bStatus) return bStatus;
			//enter Email
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Email")), objLeadTrackerDtls.get("Email"));
			if(!bStatus) return bStatus;
			//Enter Mobile number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("cellphone")), objLeadTrackerDtls.get("CellPhone"));
			if(!bStatus) return bStatus;
			//click continue
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;
			//wait for next page to load
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("start your investment")), iSync);
			if(!bStatus) return bStatus;
			//Check next page loaded
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("start your investment")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "Enter lead tracker", "Succesfully entered data to leadtracker");

		}
		catch(Exception e)
		{

		}
		return bStatus;
	}

	public static boolean enterInvestmentDetails(Map<String,String> objInvestmentDtls) throws InterruptedException {
		try {
			
			//Enter date of birth
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("date")), objInvestmentDtls.get("DateOfBirth"));
			if(!bStatus) return bStatus;
			//enter investment duration
			bStatus=CommonFunc.selectDropDown(objInvestmentDtls.get("investmentDuration"));
			if(!bStatus) return bStatus;
			//Enter Monthly Contribution
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("currency")), objInvestmentDtls.get("MonthlyContribution"));
			if(!bStatus) return bStatus;
			//select radio button for How would you like to increase your payments?
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.radioBtnInvestmentPage("Increase")));
			if(!bStatus) return bStatus;
			//select % increase 
			bStatus=CommonFunc.selectDropDown("15%");
			if(!bStatus) return bStatus;

			//select investment plan 
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.RaApp.checkBoxInvestmentPage("Low Risk")));
			if(!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.checkBoxInvestmentPage("Low Risk")));
			if(!bStatus) return bStatus;
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.RaApp.checkBoxInvestmentPage("High Risk")));
			if(!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.checkBoxInvestmentPage("High Risk")));
			if(!bStatus) return bStatus;

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("How would you like to split")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("How would you like to split")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "enterInvestmentDetails", "Succesfully entered data to Investment page");

		}
		catch(Exception e) {

		}
		return bStatus;
	}

	public static boolean splitYourInvestment() {

		try {
			//Enter split %
			bStatus=CommonFunc.enterTextForSplitPercentage(By.xpath(Locators.RaApp.enterSplitPercentage("Old Mutual Stable")), "40");
			if(!bStatus)  return bStatus;
			//Enter split %
			bStatus=CommonFunc.enterTextForSplitPercentage(By.xpath(Locators.RaApp.enterSplitPercentage("Old Mutual Edge")), "60");
			if(!bStatus)  return bStatus;
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("Tell us about yourself")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("Tell us about yourself")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "splitYourInvestment", "Succesfully entered data to Split Investment page");
		}

		catch(Exception e) {

		}

		return bStatus;
	}

	public static boolean personalDetails(Map<String,String> objPersonalDtls) {
		try {
			//Enter Work contact Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Work Contact Number")),objPersonalDtls.get("WorkNumber"));
			if(!bStatus) return bStatus;
			//Select dropdown for Maritial status
			bStatus=CommonFunc.selectDropDown(objPersonalDtls.get("MaritialStatus"));
			if(!bStatus) return bStatus;
			//Select dropdown for country of birth
			bStatus=CommonFunc.selectDropDownPersonalDtls("Country of Birth", objPersonalDtls.get("countryOfBirth"));
			if(!bStatus) return bStatus;
			//Select dropdown for Nationality
			bStatus=CommonFunc.selectDropDownPersonalDtls("Nationality", objPersonalDtls.get("Nationality"));
			if(!bStatus) return bStatus;
			//select radio button for Identity Document
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.radioBtnInvestmentPage("South African Identity Document")));
			if(!bStatus) return bStatus;
			//Enter Identity Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Identity Number")),"8702025800080");
			if(!bStatus) return bStatus;
			//Enter Income Tax Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Income Tax Number")),"2039060179");
			if(!bStatus) return bStatus;
			// select Preferred Correspondence Language
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.radioBtnInvestmentPage("English")));
			if(!bStatus) return bStatus;
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("Where do you live")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("Where do you live")));
			if(!bStatus) return bStatus;

			//Enter Street Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Street Number")),objPersonalDtls.get("StreetNumber"));
			if(!bStatus) return bStatus;
			//Enter Street Name
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Street Name")),objPersonalDtls.get("StreetName"));
			if(!bStatus) return bStatus;
			//Enter Suburb
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Suburb")),objPersonalDtls.get("Suburb"));
			if(!bStatus) return bStatus;
			//Enter City
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("City")),objPersonalDtls.get("City"));
			if(!bStatus) return bStatus;
			//Enter Postal code
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Postal Code")),objPersonalDtls.get("PostalCode"));
			if(!bStatus) return bStatus;
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("Additional Details")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("Additional Details")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "personalDetails", "Succesfully entered data to Personal details page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}

	public static boolean enterPersonalDetails(Map<String,String> objPersonalDtls) {
		try {
			
			//Title
			bStatus=CommonFunc.selectDropDown(objPersonalDtls.get("Title"));
			if(!bStatus) return bStatus;
			
			//Middle Names
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Middle name(s)")),objPersonalDtls.get("MiddleNames"));
			if(!bStatus) return bStatus;
			
			//Email
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Email")),objPersonalDtls.get("Email"));
			if(!bStatus) return bStatus;
			
			//Marital Status
			bStatus=RA.selectDropDownPersonalDtls2("Marital status",objPersonalDtls.get("MaritialStatus"));
			if(!bStatus) return bStatus;
			
			//Country of Birth
			bStatus=RA.selectDropDownPersonalDtls2("Country of birth",objPersonalDtls.get("CountryOfBirth"));
			if(!bStatus) return bStatus;
			
			//Nationality
			bStatus=RA.selectDropDownPersonalDtls2("Nationality", objPersonalDtls.get("Nationality"));
			if(!bStatus) return bStatus;
			
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.inputText("South African I.D. number")), iSync);
			
			//South African ID
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("South African I.D. number")),objPersonalDtls.get("SouthAfricanID"));
			if(!bStatus) return bStatus;
			
			//Complex/Building
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Complex/building")),objPersonalDtls.get("ComplexBuilding"));
			if(!bStatus) return bStatus;
			
			//Street address
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Street address")),objPersonalDtls.get("StreetName"));
			if(!bStatus) return bStatus;
			
			//Suburb
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Suburb")),objPersonalDtls.get("Suburb"));
			if(!bStatus) return bStatus;
			
			//City
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("City")),objPersonalDtls.get("City"));
			if(!bStatus) return bStatus;
			
			//Postal Code
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.postalCode1()),objPersonalDtls.get("PostalCode"));
			if(!bStatus) return bStatus;
			
			//Is your postal address the same ?
			
			if(!objPersonalDtls.get("PostalAddressTheSame").equalsIgnoreCase("Yes")) {
				
				
				switch(objPersonalDtls.get("PreferredPostalMethod")) {
				
				case "Street Address":
					
					//Complex/Building
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Complex/building")),objPersonalDtls.get("ComplexBuilding2"));
					if(!bStatus) return bStatus;
					
					//Street address
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Street address")),objPersonalDtls.get("StreetName2"));
					if(!bStatus) return bStatus;
					
					//Suburb
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Suburb")),objPersonalDtls.get("Suburb2"));
					if(!bStatus) return bStatus;
					
					//City
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("City")),objPersonalDtls.get("City2"));
					if(!bStatus) return bStatus;
					
					//Postal Code
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Postal Code")),objPersonalDtls.get("PostalCode2"));
					if(!bStatus) return bStatus;
					
					break;
				
				case "PO Box":
					
					//PO Box
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("PO box")),objPersonalDtls.get("POBox"));
					if(!bStatus) return bStatus;
					
					//Post Office
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Post office")),objPersonalDtls.get("PostOffice"));
					if(!bStatus) return bStatus;
					
					//Postal Code
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Postal Code")),objPersonalDtls.get("PostalCode2"));
					if(!bStatus) return bStatus;
					
					break;
					
				case "Private Bag":
					
					//Private bag number
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Private bag number")),objPersonalDtls.get("PrivateBagNumber"));
					if(!bStatus) return bStatus;
					
					//Post Office
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Post office")),objPersonalDtls.get("PostOffice"));
					if(!bStatus) return bStatus;
					
					//Postal Code
					bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Postal Code")),objPersonalDtls.get("PostalCode2"));
					if(!bStatus) return bStatus;
					
					break;
				}
				
				
			}
			
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("CONTINUE")));
			if(!bStatus) return bStatus;	
	
			Reporting.logResults("Pass", "personalDetails", "Succesfully entered data to Personal details page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean additionalDetails() {
		try {

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("Beneficiary details")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("Beneficiary details")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "additionalDetails", "Succesfully entered data to Additional details page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}


	public static boolean enterBeneficiaryDetailsPage(Map<String,String> objBeneficiariesDtls) {
		try {
				
				int count = 0;
			
				for(int counter = 1; counter < 11; counter++) {
									
					if(!objBeneficiariesDtls.get("BenficiariesTitle" + counter).isEmpty()) {
						
						//Add beneficiary
						bStatus=Elements.clickElement(By.xpath(Locators.RaApp.addBeneficiary()));
						if(!bStatus) return bStatus;	
						
						//Title
						bStatus=RA.selectDropDownPersonalDtls2("Title",objBeneficiariesDtls.get("BenficiariesTitle" + counter));
						if(!bStatus) return bStatus;
						
						//First Name
						bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("First name(s)")),objBeneficiariesDtls.get("BenficiariesFirstName" + counter));
						if(!bStatus) return bStatus;
						
						//Surname
						bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Surname")),objBeneficiariesDtls.get("BenficiariesSurname" + counter));
						if(!bStatus) return bStatus;
						
						//Date Of Birth
						bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Date of birth")),objBeneficiariesDtls.get("BenficiariesDateOfBirth" + counter));
						if(!bStatus) return bStatus;
						
						//Gender
						bStatus=RA.selectDropDownPersonalDtls2("Gender",objBeneficiariesDtls.get("BenficiariesGender" + counter));
						if(!bStatus) return bStatus;
						
						if(!objBeneficiariesDtls.get("BenficiariesMobileNumber" + counter).isEmpty()) {
							//Mobile Number
							bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Mobile number")),objBeneficiariesDtls.get("BenficiariesMobileNumber" + counter));
							if(!bStatus) return bStatus;
						}
						
						if(!objBeneficiariesDtls.get("BenficiariesEmail" + counter).isEmpty()) {
							//Email
							bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Email")),objBeneficiariesDtls.get("BenficiariesEmail" + counter));
							if(!bStatus) return bStatus;		
						}
						
						//Save beneficiary
						bStatus=Elements.clickElement(By.xpath(Locators.RaApp.saveBeneficiary()));
						if(!bStatus) return bStatus;	
						
					}
					
					count = counter;
					
					
				}
			
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			
			if(count >= 2) {
				
				if(!objBeneficiariesDtls.get("EvenSplit").equalsIgnoreCase("Yes")) {
					
					
					for(int counter = 1; counter <11; counter++) {
						
						if(!objBeneficiariesDtls.get("BenficiariesPercentage" + counter).isEmpty()) {
							//Percentage Split
							bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputForText("percentage-field-" + counter)),objBeneficiariesDtls.get("BenficiariesPercentage" + counter));
							if(!bStatus) return bStatus;	
						}
	
						
					}
					
					
				}
				
				//Even Split
				bStatus=Elements.clickElement(By.xpath(Locators.RaApp.evenSplitCheckBox()));
				if(!bStatus) return bStatus;
				
				
				//click continue 
				bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
				if(!bStatus) return bStatus;
			}
			
			
			
			
			Reporting.logResults("Pass", "beneficiaryPage", "Succesfully entered data to Beneficiary page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean beneficiaryPage() {
		try {

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath("//strong[text()='Final Checks']"), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath("//strong[text()='Final Checks']"));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "beneficiaryPage", "Succesfully entered data to Beneficiary page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}

	public static boolean finalChecks(Map<String,String> objFinalCheck) {
		try {

			//select source of funds
			bStatus=CommonFunc.selectDropDownPersonalDtls("Source of Funds", objFinalCheck.get("SourceOfFunds"));
			if(!bStatus) return bStatus;
			//select source of Income
			bStatus=CommonFunc.selectDropDownPersonalDtls("Source of Income", objFinalCheck.get("SourceOfIncome"));
			if(!bStatus) return bStatus;
			//select Employment Position
			bStatus=CommonFunc.selectDropDownPersonalDtls("Employment Position", objFinalCheck.get("EmploymentPosition"));
			if(!bStatus) return bStatus;
			//select Nature Of Business
			bStatus=CommonFunc.selectDropDownPersonalDtls("Nature Of Business", objFinalCheck.get("NatureOfBusiness"));
			if(!bStatus) return bStatus;
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle("Payment details")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle("Payment details")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "finalChecks", "Succesfully entered data to final Checks page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}


	public static boolean paymentDetails(Map<String,String> objPaymnetDtls) {
		try {

			//Enter account holder name
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Account Holder Name")), objPaymnetDtls.get("AccountHolderName"));
			if(!bStatus) return bStatus;

			//select Debit Order Date
			bStatus = Elements.clickElement(By.xpath("//span[contains(text(),'Debit Order Date')]//..//div[@class='om-dropdown-field']"));
			if(!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath("//span[contains(text(),'Debit')]//..//ul//li"));
			if(!bStatus) return bStatus;

			//select Bank Name
			bStatus=CommonFunc.selectDropDownPersonalDtls("Bank Name", objPaymnetDtls.get("BankName"));
			if(!bStatus) return bStatus;
			//Enter Account Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Account Number")), objPaymnetDtls.get("AccountNumber"));
			if(!bStatus) return bStatus;
			//select Bank Account Type
			bStatus=CommonFunc.selectDropDownPersonalDtls("Bank Account Type", objPaymnetDtls.get("AccountType"));
			if(!bStatus) return bStatus;

			//select radio button to confirm terms and conditions
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.RaApp.checkBoxPaymentDetails("TERMS AND CONDITIONS")));
			if(!bStatus) return bStatus;
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.checkBoxPaymentDetails("TERMS AND CONDITIONS")));
			if(!bStatus) return bStatus;

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle2("almost done")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle2("almost done")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "paymentDetails", "Succesfully entered data to Payment Details page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean enterPaymentDetails(Map<String,String> objPaymentDtls) {
		try {

			
			//Bank
			bStatus=CommonFunc.selectDropDownPersonalDtls("Bank Name", objPaymentDtls.get("BankName"));
			if(!bStatus) return bStatus;
			
			//Account Number
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputText("Account Number")), objPaymentDtls.get("AccountNumber"));
			if(!bStatus) return bStatus;
			
			//Account Type
			bStatus=CommonFunc.selectDropDownPersonalDtls("Bank Account Type", objPaymentDtls.get("AccountType"));
			if(!bStatus) return bStatus;
			
			//Debit Order Date
			bStatus=CommonFunc.selectDropDownPersonalDtls("Select a day for your debit order", objPaymentDtls.get("DayOfDebitOrder"));
			if(!bStatus) return bStatus;
			
			//Debit Order Starting Month
			bStatus=CommonFunc.selectDropDownPersonalDtls("Select a start month", objPaymentDtls.get("StartMonth"));
			if(!bStatus) return bStatus;
			
			//Authorise
			bStatus=Elements.checkWebElement(By.xpath(Locators.RaApp.checkBoxPaymentDetails("I hereby authorise Old Mutual to withdraw all contributions that I undertake to pay towards my Old Mutual Invest Plan from this bank account. I also confirm (1) that the account details are correct and (2) that I am authorised to give this instruction.")));
			if(!bStatus) return bStatus;
						
			//Source of income
			bStatus=CommonFunc.selectDropDownPersonalDtls("Source of income", objPaymentDtls.get("SourceOfIncome"));
			if(!bStatus) return bStatus;
			
			//Source of funds
			bStatus=CommonFunc.selectDropDownPersonalDtls("Source of funds", objPaymentDtls.get("SourceOfFunds"));
			if(!bStatus) return bStatus;
			
			//Employment
			bStatus=CommonFunc.selectDropDownPersonalDtls("What do you do for a living?", objPaymentDtls.get("EmploymentPosition"));
			if(!bStatus) return bStatus;
			
			//Industry
			bStatus=CommonFunc.selectDropDownPersonalDtls("What industry do you work in?", objPaymentDtls.get("NatureOfBusiness"));
			if(!bStatus) return bStatus;
			
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "paymentDetails", "Succesfully entered data to Payment Details page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}


	public static boolean confirmationPage() {
		try {

			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("Continue")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.subHeaderTitle2("please confirm if you are happy with your purchase")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.subHeaderTitle2("please confirm if you are happy with your purchase")));
			if(!bStatus) return bStatus;

			//click Submit 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnBackToHome("Submit")));
			if(!bStatus) return bStatus;

			//Check next page loaded
			bStatus=Wait.waitForElementVisibility(By.xpath(Locators.RaApp.btnBackToHome("Back to Home")), iSync);
			if(!bStatus) return bStatus;
			bStatus=Verify.verifyElementVisible(By.xpath(Locators.RaApp.btnBackToHome("Back to Home")));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "confirmationPage", "Succesfully entered data to confirmation page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean confirmationPageFinalChecks() {
		try {

			//checkboxes
			bStatus=Elements.checkWebElement(By.xpath(Locators.RaApp.confirmationPageCheckBox("I understand that replacing any investment or insurance")));
			if(!bStatus) return bStatus;
			
			bStatus=Elements.checkWebElement(By.xpath(Locators.RaApp.confirmationPageCheckBox("I accept the")));
			if(!bStatus) return bStatus;
			
			//click submit 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.submitButton()));
			if(!bStatus) return bStatus;

			Reporting.logResults("Pass", "confirmationPage", "Succesfully entered data to confirmation page");
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean retirementAnnuityApplicationPage(Map<String,String> objRADtls) {
		
		try {
			
			bStatus = Wait.waitForElementVisibility(By.xpath(Locators.RaApp.optimalRetirementAnnuityPage()), iSync);
			if(!bStatus) return bStatus;
			
			bStatus = Elements.clickElement(By.xpath(Locators.RaApp.readyToStartButton()));
			if(!bStatus) return bStatus;
			
			bStatus = Wait.waitForElementVisibility(By.xpath(Locators.RaApp.optimalRetirementAnnuityPage()), iSync);
			if(!bStatus) return bStatus;
			
			bStatus = Elements.enterText(By.xpath(Locators.RaApp.inputText("First name")), objRADtls.get("FirstName"));
			if(!bStatus) return bStatus;
			
			bStatus = Elements.enterText(By.xpath(Locators.RaApp.inputText("Surname")), objRADtls.get("LastName"));
			if(!bStatus) return bStatus;
			
			bStatus = Elements.enterText(By.xpath(Locators.RaApp.inputText("Mobile number")), objRADtls.get("CellPhone"));
			if(!bStatus) return bStatus;
			
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("CONTINUE")));
			if(!bStatus) return bStatus;
			
			Reporting.logResults("Pass", "retirementAnnuityApplicationPage", "Succesfully entered data to optimal retirement annuity application page");
			
		}
		catch(Exception e)
		{

		}
		return bStatus;
	}
	
	public static boolean enterInvestmentSetupDetails(Map<String,String> objInvestmentDtls) throws InterruptedException {
		try {
			
			bStatus = Wait.waitForElementVisibility(By.xpath(Locators.RaApp.optimalRetirementAnnuityPage()), iSync);
			if(!bStatus) return bStatus;
			
			bStatus = Elements.clickElement(By.xpath(Locators.RaApp.readyToStartButton()));
			if(!bStatus) return bStatus;
			
			bStatus = Wait.waitForElementVisibility(By.xpath(Locators.RaApp.optimalRetirementAnnuityPage()), iSync);
			if(!bStatus) return bStatus;
			
			//Enter date of birth
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputForText("age")), objInvestmentDtls.get("Age"));
			if(!bStatus) return bStatus;
			//enter investment duration
			bStatus=CommonFunc.selectDropDown(objInvestmentDtls.get("RetirementAge"));
			if(!bStatus) return bStatus;
			//Enter Monthly Contribution
			bStatus=Elements.enterText(By.xpath(Locators.RaApp.inputForText("monthlyInvestment")), objInvestmentDtls.get("MonthlyContribution"));
			if(!bStatus) return bStatus;
			
			Thread.sleep(5000);
			
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.RaApp.radioBtnPerferRA(objInvestmentDtls.get("InvestmentType"))));
			
			//What would you prefer?	
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.radioBtnPerferRA(objInvestmentDtls.get("InvestmentType"))));
			if(!bStatus) return bStatus;
			//What type of investor are you?
			
			bStatus=CommonFunc.scrollToViewElement(By.xpath(Locators.RaApp.radioBtnInvestmentPage2(objInvestmentDtls.get("InvestorType"))));
			
			
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.radioBtnInvestmentPage2(objInvestmentDtls.get("InvestorType"))));
			if(!bStatus) return bStatus;

			
			//click continue 
			bStatus=Elements.clickElement(By.xpath(Locators.RaApp.btnContinue("CONTINUE")));
			if(!bStatus) return bStatus;			

			Reporting.logResults("Pass", "enterInvestmentSetupDetails", "Succesfully entered data to Investment Setup page");

		}
		catch(Exception e) {

		}
		return bStatus;
	}
	
	public static boolean selectDropDownPersonalDtls2(String sFieldName,String sValue)
	{
		//enter investment duration
		//click on the drop down for investment duration
		bStatus = Elements.clickElement(By.xpath(Locators.RaApp.personalDetailsFieldDropdown(sFieldName)));
		//bStatus=Elements.selectOptionByValue(By.xpath(Locators.RaApp.dropDown("select")), "Doctor");
		if(!bStatus) return bStatus;
		//Select drop down value for investment duration
		bStatus =  Elements.clickElement(By.xpath(Locators.RaApp.personalDetailsDropdown(sFieldName, sValue)));
		if(!bStatus) return bStatus;

		return bStatus;
	}
	
}
