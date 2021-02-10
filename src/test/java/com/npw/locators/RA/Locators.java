package com.npw.locators.RA;

import com.sun.jna.platform.win32.LMAccess.LOCALGROUP_INFO_0;

public class Locators 
{
	public static String sXpath = "";


	public static class NavigationMenu
	{
		public static String getHeaderMenu(String sAppMenuItem)
		{
			//sXpath = "//div[contains(@class,'container-desktop')]//a[@href='/"+sAppMenuItem+"' or text()='"+sAppMenuItem+"']";
			//sXpath="//div[@class='om-main-navigation-col-2 main-navigation-items-desktop']/om-main-navigation-menu/ul/li/a[@href='/"+sAppMenuItem+"']";
			sXpath="//div[contains(@class,'container-desktop')]//a[text()='"+sAppMenuItem +"']";
			//sXpath="//div[contains(@class,'container-desktop')]//li[@class='active']//a[text()='"+sAppMenuItem+"']";
			//div[contains(@class,'container-desktop')]//a[text()='Wealth']
			//div[contains(@class,'container-desktop')]//a[text()='personal']
			return sXpath;
		}

		public static String subHeaderHover(String sHoverMenu)
		{
			//sXpath="//div[contains(@class, 'desktop desktop-scroll-nav')]//a[@href=\"/personal/solutions\" and contains(text(), 'Our  ')]";
			//sXpath="//div[contains(@class,'container-desktop')]//li[@class='active']//a[text()='"+sHoverMenu+"']";
			sXpath="//div[contains(@class,'container-desktop')]//li[@class='active']//*[text()='"+sHoverMenu+"']";
			return sXpath;
		}
		
		public static String AppBuyNow(String sText) {
			
			sXpath="//span[contains(text(),'"+sText+"')]//ancestor::div[@class='om-product-card-content-container']//*[contains(text(),'BUY NOW')]";
			return sXpath;
			
		}

	}

	public static class RaApp
	{
		public static String selectRaPlan(String sRaPlaneName)
		{
			sXpath="//div[contains(@class,'comparison-table-desktop-container')]//a[contains(@href, '"+sRaPlaneName+"')] ";
			//div[contains(@class,'comparison-table-desktop-container')]//a[contains(@href, '/retirement-ann')] 
			return sXpath;
		}

		public static String btnContinue(String btnContinue)
		{
			sXpath="//*[text()='"+btnContinue+"']";
			return sXpath;
		}

		public static String btnBackToHome(String btnBackHome)
		{
			sXpath="//*[contains(text(), '"+btnBackHome+"')]";
			return sXpath;
		}


		public static String inputText(String Stext)
		{
			
			sXpath="//input[((@type='text') and (preceding-sibling::span[text()='"+Stext+"'] or @placeholder='"+Stext+"')) or "
					+ "ancestor::om-input-field[contains(@preset,'"+Stext+"') or contains(@placeholder,'"+Stext+"')]]";

			return sXpath;
		}
		
		public static String inputForText(String Stext)
		{
			
			sXpath="//*[@for='"+Stext+"']";

			return sXpath;
		}

		public static String clickDropDown(String sText)
		{
			
			sXpath="//select//option[contains(text(),'"+sText+"')]//..//..//..//div[@class='om-dropdown-field']";
			return sXpath;
		}

		public static String dropDown(String sText)
		{
			
			sXpath="//ul[@class='dropdown-options-list']//li[text()='"+sText+"']";
			return sXpath;
		}

		public static String subHeaderTitle(String sSubHeaderTtl)
		{
			sXpath="//*[contains(text(), '"+sSubHeaderTtl+"')]";
			return sXpath;
		}

		public static String subHeaderTitle2(String sSubHeaderTtl)
		{
			
			sXpath="//strong[contains(text(),'"+sSubHeaderTtl+"')]";
			return sXpath;
		}

		public static String checkBoxInvestmentPage(String Stext)
		{
			
			sXpath="//h4[contains(text(),'"+Stext+"')]/ancestor::div[@class='product-info-accordion']//om-check-box";
			return sXpath;
		}

		public static String radioBtnInvestmentPage(String Stext)
		{
			sXpath="//om-radio-button[contains(@radio-button-text,'"+Stext+"')]";
			return sXpath;
		}
		
		
		public static String radioBtnInvestmentPage2(String Stext)
		{
			sXpath="//om-radio-button-accordion-item[@radio-button-title='"+Stext+"']//om-radio-button";
			return sXpath;
		}
		
		public static String radioBtnPerferRA(String Stext)
        {
               
               sXpath="//om-radio-button[contains(@radio-button-text,'"+Stext+"')]";
               return sXpath;
        }

		public static String enterSplitPercentage(String sText)
		{
			sXpath="//h6[@class='medium' and contains(text(),'"+sText+"')]//..//..//..//om-input-field[@suffix=\"%\"]//input[contains(@class,'om-input-field')]";
			return sXpath;
		}

		public static String dropDownPersonalDtls(String sFieldName, String sValue)
		{
			sXpath="//span[contains(text(),'"+sFieldName+"')]//..//ul[@class='dropdown-options-list']//li[text()='"+sValue+"']";
			return sXpath;
		}

		public static String checkBoxPaymentDetails(String Stext)
		{
			
			sXpath="//om-check-box[contains(@text,'"+Stext+"')]//span[@class='om-checkbox-icon']";
			return sXpath;
		}
		public static String allLinksHomePage()
		{
			sXpath="//a[contains(@href,'co.za')]";
			return sXpath;
		}
		
		public static String optimalRetirementAnnuityPage() {
			return "//strong[text()='Optimal Retirement Annuity Application']";
		}
		
		public static String readyToStartButton() {
			return "//span[contains(text(),'ready to start')]";
		}
		
		public static String addBeneficiary() {
			return "//om-ra-button[@text='ADD BENEFICIARY']";
		}
		
		public static String confirmationPageCheckBox(String Stext) {
			return "//span[contains(text(),'"+ Stext +"')]//..//..//span[@class='om-checkbox-icon']";
		}
		
		public static String submitButton() {
			return "//span[text()='SUBMIT']";
		}
		
		public static String evenSplitCheckBox() {
			return "//span[@class='Even split']";
		}
		
		public static String investmentSetupRadioButtons(String Stext) {
			return "//div[text()='"+Stext+"']//..//..//div[@class='om-radio-button-wrapper']";
		}
		
		public static String personalDetailsDropdown(String Stext,String value) {
			return "//p[contains(text(),'"+Stext+"')]//..//ul[contains(@class,'dropdown-options-list')]//li[text()='"+value+"']";
		}
		
		public static String personalDetailsFieldDropdown(String Stext) {
			return "//p[contains(text(),'"+Stext+"')]//..//div[@class='om-dropdown-field']";
		}
		
		public static String postalCode1() {
			return "//om-form-input-field-wrapper[@id='postalCode']//om-form-input-field[@form-id='personalDetailsForm']//input[@placeholder='Postal code']";
		}
		
		public static String saveBeneficiary() {
			return "//span[text()='SAVE BENEFICIARY']";
		}

	}




	public static class GapApp {


		public static String btnContinueOrPrevious(String btnContinueOrPrevious)
		{
			return RaApp.btnBackToHome(btnContinueOrPrevious);

		}

		public static String inputText(String Stext) {
			sXpath="//input[((@type='text') and (preceding-sibling::span[text()='"+Stext+"'] or @placeholder='"+Stext+"'))]";
			return sXpath;
		}

		public static String radioBtn(String stext) {
			// TODO Auto-generated method stub
			return RaApp.radioBtnInvestmentPage(stext);	
		}

		public static String clickDropDown(String sText)
		{

			return RaApp.clickDropDown(sText);
		}

		public static String selectDropDownValue(String sText)
		{
			return RaApp.dropDown(sText);
		}

		public static String selectCheckBox(String sText) {
			sXpath="//span[contains(text(),'"+sText+"')]//..//preceding-sibling::om-check-box//span[@class='om-checkbox-icon']";
			return sXpath;
		}

		public static String getSuccessMsg() {
			sXpath="//div[@slot='content']//strong[contains(text(),'Application')]//..//following-sibling::h6";
			return sXpath;
		}
		
		public static String subHeaderTitle(String sSubHeaderTtl)
		{
			
			return RaApp.subHeaderTitle(sSubHeaderTtl);
		}
		
		public static String ShadowDom(String sText) {
			
			sXpath= "//strong[contains(text(),'"+sText+"')]//ancestor::div[@class='toggle-heading-wrapper']//following-sibling::div[@class=('form-fields-wrapper' or 'toggle-wrapper')]//om-toggle-switch | //strong[contains(text(),'"+sText+"')]//..//following::om-toggle-switch[1]";
			return sXpath;
			
		}
		
		

	}


	
	
	public static class NPW
    {
           public static String HeaderMenu(String sAppMenuItem)
           {
                  sXpath="//div[contains(@class,'container-desktop')]//a[text()='"+sAppMenuItem +"']";
                  return sXpath;
           }
           
           public static String FooterMenu(String sAppMenuItem)
           {
                  sXpath="//div[contains(@class,'om-footer-container')]//a[text()='"+sAppMenuItem +"']";
                  return sXpath;
           }
           
           public static String btnGeneric(String btnContinue)
           {
                  sXpath="//*[text()='"+btnContinue+"']";
                  return sXpath;
           }
           
           public static String btnCallMeBack(String btnCallMeBack)
           {
                  sXpath="//button[contains(@class,'icon-checkbox om-check-box--unchecked')]//span[text()='"+btnCallMeBack +"']";
                  return sXpath;
           }
           
           public static String weCallMeBackResponse()
           {
                  sXpath="//div[contains(@class,'side-drawer-content-wrapper')]//h5[@class='medium']";
                  return sXpath;
           }
           
           public static String btnchatbot()
           {
                  sXpath="//*[@id=\"nba-web-chatbot-main-container\"]/div/button";
                  return sXpath;
           }
           public static String btnclosechatbot()
           {
                  sXpath="//*[@id=\"nba-web-chatbot-main-container\"]/div/button";
                  return sXpath;
           }
           public static String searchicon()
           {
           
           sXpath="//div[contains(@class,'om-main-navigation-col-3__row-2')]//div[@class='om-main-navigation-search-wrapper']";
           return sXpath;
           
           
           }
           
           public static String radioBtnAdvisorPage(String Stext)
           {
                  //sXpath="//span[contains(text(),'Increase')]//..//input[@type='radio']";
                  sXpath="//om-radio-button[contains(@radio-button-text,'"+Stext+"')]";
                  return sXpath;
           }
           
           public static String inputTextAdvisor(String Stext)
           {
                  sXpath= "//*[@placeholder='"+Stext+"']";
                  return sXpath;
           }
           
           public static String checkBox(String Stext)
           {
                  
                  sXpath="//om-check-box[contains(@text,'"+Stext+"')]//span[@class='om-checkbox-icon']";
                  return sXpath;
           }
           
           public static String clickdropdown()
           {
                  
                  sXpath="//*[contains(@class,'om-dropdown-field__arrow')]";
                  return sXpath;
           }
           
           
           
           
           public static String navigationforward()
           {
                  sXpath="//div[contains(@class,'pagination-wrapper-desktop')]//div[contains(@class,'button-wrapper active')][2]";
                  //sXpath ="//span[contains(@class,'om-icon-container')]//..//..//div[contains(@class,'button-wrapper active')]";
                  //sXpath="//*[@id='app']/om-faa-results-page/om-page/div/div/om-section/div/div/div[2]/div/div/div/om-pagination/div/div[1]/div[3]/om-icon";
                  return sXpath;
           }
           
           public static String navigationback()
           {
                  sXpath="//div[contains(@class,'pagination-wrapper-desktop')]//div[contains(@class,'button-wrapper active')]";
                  //sXpath ="//span[contains(@class,'om-icon-container')]//..//..//div[contains(@class,'button-wrapper active')]";
                  //sXpath="//*[@id='app']/om-faa-results-page/om-page/div/div/om-section/div/div/div[2]/div/div/div/om-pagination/div/div[1]/div[3]/om-icon";
                  return sXpath;
           }
           
           public static String navigatetofirst()
           {
                  sXpath="//om-pagination-item//div[contains(text(),'1')]";
                  //sXpath ="//span[contains(@class,'om-icon-container')]//..//..//div[contains(@class,'button-wrapper active')]";
                  //sXpath="//*[@id='app']/om-faa-results-page/om-page/div/div/om-section/div/div/div[2]/div/div/div/om-pagination/div/div[1]/div[3]/om-icon";
                  return sXpath;
           }
           
           
           
           public static String advisorimage(String advisor)
           {
                  sXpath="//div[(@class='card-image' and contains(@style,'"+advisor+"'))]";
                  return sXpath;
           }
           
           public static String advisorname(String advisor,String name)
           {
                  sXpath="//div[(@class='card-image' and contains(@style,'"+advisor+"'))]//..//..//span[contains(text(),'"+name+"')]";
                  return sXpath;
           }
           
           public static String advisorimage()
           {
                  sXpath="//div[(@class='card-image')]";
                  return sXpath;
           }
           
           public static String advisorname()
           {
                  sXpath="//div[(@class='card-image')]//..//..//span[(@slot='product-title')]";
                  return sXpath;
           }
           
           
           
           
    }
	



}
