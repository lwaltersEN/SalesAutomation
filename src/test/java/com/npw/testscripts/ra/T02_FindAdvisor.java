package com.npw.testscripts.ra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.npw.lib.RA.CommonFunc;
import com.npw.locators.RA.Locators;
import com.om.framework.basetest.BaseTest;
import com.om.framework.lib.Browser;
import com.om.framework.lib.Elements;
import com.om.framework.lib.Utilities;

public class T02_FindAdvisor extends BaseTest {
	
	
	
	private static Map<String,String> objRADtls;
	private static String TestData_path= "TestData\\NPW_FindAdviser.xlsx";
	private static String sheetName="NPW";
	private static String sUrl = "https://www.oldmutual.co.za/personal/find-adviser";
	private static boolean bStatus;
	private static int inamecount=0;
	private static int icountnavf=0;
	
	private static String sTown;
	private static String sTownpre;
	private static String sFname;
	private static String sLname;
	private static String sAdvisor;
	private static String sAdvisorname;
    public static String sAdvisorCode;
    public static String  sAdvisorFullName;
    public static String sAdvisorCodeJ;
    public static String sAdvisorCodeK;
    private static String sStatus;
	
	public static ArrayList<String> arrAdvisorCode = new ArrayList<>();
	public static ArrayList<String> arrAdvisorName = new ArrayList<>();
    public static ArrayList<String> arrkeys = new ArrayList<>();
    ArrayList<String> arrSubLinks4 = new ArrayList<>();
   public static List <WebElement> images = new ArrayList<>();
   public static List <WebElement> names = new ArrayList<>();
  public static HashMap<String, String> imageandname = new HashMap<String, String>();
	
	
	
@Test
public static void findAdvisor() throws Exception 
{
		// TODO Auto-generated method stub
	
	
	try {
		addRecords();
		verifyRecords();
	}
	
	catch(Exception e)
	{
		
	}
	
	
				
				
}//Function Ends




public static void addimageandname(String Name)
{
	
	names=Elements.getWebElements(By.xpath(Locators.NPW.advisorname()));
	images=Elements.getWebElements(By.xpath(Locators.NPW.advisorimage()));
	if(names.size()==images.size()) {
		for(int namecout=0;namecout<=names.size()-1;namecout++)
		{	System.out.println(names.size());
			sAdvisorname=names.get(namecout).getText().trim();
			sAdvisorCode=images.get(namecout).getAttribute("style");
			if(sAdvisorCode.contains("default-adviser-image.jpg")||sAdvisorCode.contains("undefined"))
			{
				
				sAdvisorCode = sAdvisorCode +"/"+sAdvisorname; 
			}
			
			
			if(!sAdvisorname.equalsIgnoreCase(null) && !sAdvisorCode.equalsIgnoreCase(null) )
			{	
				imageandname.put(sAdvisorCode, sAdvisorname);
				
			}
			
			
		}
	}
	
}

public static void addRecords() throws Exception {

	Utilities.setExcelFile(TestData_path,sheetName);		
	
    int iRowCount = Utilities.getRowNum();
	
for(int iRowCounter=1;iRowCounter<iRowCount;iRowCounter++)
{
	try {	
		//ExcelUtilities.setExcelFile(TestData_path,sheetName);
		sTown=Utilities.getCellData(iRowCounter,0);
		sTownpre=Utilities.getCellData(iRowCounter-1,0);
		sFname=Utilities.getCellData(iRowCounter,1);
		sLname= Utilities.getCellData(iRowCounter,2);
		sAdvisor= Utilities.getCellData(iRowCounter,3);
		sAdvisorCodeJ=sAdvisor + ".jpg";
		sAdvisorFullName= sFname.trim() + " " + sLname.trim();
		sAdvisorFullName=sAdvisorFullName.trim();
		
		
	
		
		
	//Check for town is not null	
	if((!sTown.equalsIgnoreCase(sTownpre))) 
	{
		Utilities.setCellData("Checked",iRowCounter,5);
		bStatus=NavigateToAdvisor(sTown);
		if(!bStatus) return;
		
		
		bStatus=naviteam();
		if(bStatus)
		{
			addimageandname(sAdvisorFullName);
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.navigationback()));
			if(!bStatus) return;
		}
		
		
		
		
		
		do
		{
			addimageandname(sAdvisorFullName);
			bStatus=navforward();
			if(bStatus)
			{
			bStatus=Elements.clickElement(By.xpath(Locators.NPW.navigationforward()));
			bStatus=true;
			}
			else
			{
				bStatus=false;
			}
			
		}while(bStatus);
		
	}
	
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		
}//For Loop Ends

			
	
}


public static void verifyRecords() throws Exception
{
	
	Utilities.setExcelFile(TestData_path,sheetName);		
	
    int iRowCount = Utilities.getRowNum();
	
for(int iRowCounter=1;iRowCounter<iRowCount;iRowCounter++)
{
	bStatus=false;
	sStatus ="Faild Record Not Present";
	try {	
		//ExcelUtilities.setExcelFile(TestData_path,sheetName);
		sTown=Utilities.getCellData(iRowCounter,0);
		sTownpre=Utilities.getCellData(iRowCounter-1,0);
		sFname=Utilities.getCellData(iRowCounter,1);
		sLname= Utilities.getCellData(iRowCounter,2);
		sAdvisor= Utilities.getCellData(iRowCounter,3);
		sAdvisorCodeJ=sAdvisor + ".jpg";
		sAdvisorCodeK=sAdvisor + ".JPG";
		sAdvisorFullName= sFname.trim() + " " + sLname.trim();
		sAdvisorFullName=sAdvisorFullName.trim();
		
	
		
		for (Map.Entry<String, String> entry : imageandname.entrySet()) 
		{
			
			if(entry.getKey().contains(sAdvisorCodeJ)||entry.getKey().contains(sAdvisorCodeK))
			{
				
				if(entry.getValue().equalsIgnoreCase(sAdvisorFullName))
				{
					bStatus=true;
					sStatus="Pass";
					break;
					
				}
				else
				{
					
					bStatus=false;
					sStatus="Pass but name is not correct expected :"+sAdvisorFullName+"Actual: "+entry.getValue()+"";
					break;
					
				}
			}
			if(entry.getKey().contains("default-adviser-image.jpg")) 
			{
				
				if(entry.getValue().equalsIgnoreCase(sAdvisorFullName))
				{
					bStatus=true;
					sStatus="Pass default-adviser-image.jpg";
					break;
					
				}
			}
			if(entry.getKey().contains("undefined")) 
			{
				
				if(entry.getValue().equalsIgnoreCase(sAdvisorFullName))
				{
					bStatus=true;
					sStatus="Failed White Space";
					break;
					
				}
			}
			
			
			
				
			
		    System.out.println(entry.getKey() + " = " + entry.getValue());
		}
		
		if(bStatus)
		{
			Utilities.setCellData(sStatus,iRowCounter,4);
			
		}
		else
		{
			Utilities.setCellData(sStatus,iRowCounter,4);
		}
		
		
		
	}
	catch(Exception e)
	{
		
	}
	
}//For Loop Ends

Utilities.closeexcel(TestData_path);
	
}
	

	
	public static boolean advisorname(String advisor,String name) throws InterruptedException
	{
		bStatus=false;
		inamecount=Elements.getXpathCount(By.xpath(Locators.NPW.advisorname(advisor,name)));
		if(inamecount>0)
		{
			System.out.println(inamecount);
			bStatus=true;
			
		}
		return bStatus;
	}
	
	public static boolean navforward() throws InterruptedException
	{
		bStatus=false;
		icountnavf=Elements.getXpathCount(By.xpath(Locators.NPW.navigationforward()));
		if(icountnavf>0)
		{
			System.out.println(icountnavf);
			bStatus=true;
			
		}
		return bStatus;
	}
	
	public static boolean naviteam() throws InterruptedException
	{
		bStatus=false;
		icountnavf=Elements.getXpathCount(By.xpath(Locators.NPW.navigationback()));
		if(icountnavf>0)
		{
			System.out.println(icountnavf);
			bStatus=true;
			
		}
		return bStatus;
	}
	
	public static boolean advisorimage(String advisor) throws InterruptedException
	{
		bStatus=false;
		inamecount=Elements.getXpathCount(By.xpath(Locators.NPW.advisorimage(advisor)));
		if(inamecount>0)
		{
			System.out.println(inamecount);
			bStatus=true;
			
		}
		return bStatus;
	}
	
	
	public static boolean NavigateToAdvisor(String town) throws InterruptedException
	{
		
		try {
			
		
		bStatus=false;
		
		Browser.navigateTo(driver,sUrl);
		bStatus=Elements.clickElement(By.xpath(Locators.NPW.radioBtnAdvisorPage("video call")));
		if(!bStatus) return bStatus;
		Thread.sleep(2000);
		
		
		bStatus=CommonFunc.selectDropDown("R20 000 - R80 000");
		if(!bStatus) return bStatus ;
		Thread.sleep(2000);
		
		bStatus=Elements.enterTextDE(By.xpath(Locators.NPW.inputTextAdvisor("Enter a location")),town);
		if(!bStatus) return bStatus;
		
		Thread.sleep(2000);
		bStatus=Elements.clickElement(By.xpath(Locators.NPW.checkBox("saving and investing")));
		if(!bStatus) return bStatus;
		Thread.sleep(2000);
		
		bStatus=Elements.clickElement(By.xpath(Locators.NPW.btnGeneric("FIND AN ADVISER")));
		if(!bStatus) return bStatus;
		Thread.sleep(2000);
		
		return bStatus;
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return bStatus;
		
	}
	
	
	

}
