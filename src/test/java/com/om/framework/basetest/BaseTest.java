package com.om.framework.basetest;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.om.framework.lib.Browser;
import com.om.framework.lib.Messages;
import com.om.framework.lib.Utilities;


public class BaseTest extends TestListenerAdapter
{
	public static List<String> stepsList = new ArrayList<>();
	public static String sBrowser = "";		
	public static String sTestSiteURL = "";
	public static String sUsername = "";
	public static String sPassword = "";
	public static String sAdminUser = "";
	public static String sAdminPW = "";
	public static String sLanguage = "";
	public static String sConfiguration = "";
	public static String sProjectName = "";
	public static String sBuild = "";
	public static String sDBPlatForm = "";
	public static String sTestCasePath = "";
	public static String sIEDriverPath = "";
	public static String sEdgeDriverPath = "";
	public static String sChromeDriverPath = "";
	public static String sChromeExtension="";
	public static String sFfDriverPath = "";
	public static String sNewParameter = "";

	//Test Case members
	public static String Subject = "";
	public static String TestName = "";
	public static String StepName = "";
	public static String Description = "";
	public static String ExpectedResult = "";	
	public static String Type = "";	
	public static String TestDate = "";

	//public static String testCaseFilename = "";
	private static Calendar cal = Calendar.getInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMddyyHHmmss");
	private static String timeSubStr = sdf.format(cal.getTime());
	public static String testCaseFilename = "\\TC_WebClient_TestCase-"+timeSubStr+".csv";

	//Test Logging members
	public static String sTestPlanLogging = "";
	public static String dbplatform = "";
	public static boolean imgCaptured = false;

	//Language setting member
	protected static String slxLanguage = "";

	public static Map<String, String> objProperties = null;

	public static FirefoxProfile ffProfile = new FirefoxProfile();
	public static WebDriver driver;


	//Browserstack members
	public static String sbStackUserName="";
	public static String sbStackpasskey="";
	public static String sbStackOS="";
	public static String sbStackOs_version="";
	public static String sbStackBrowser="";
	public static String sbStackBrowser_version="";
	public static String sbStackSelenium_version="";

	/**
	 * Launches the test webdriver browser based on the given values in the app.properties or incoming input values
	 * from the command-line or TestNG .xml.  If the app.properties does not override the input values or if the input 
	 * values are not specified from the command-line/TestNG .xml, the hard-coded default values will be used.
	 * 
	 * The input variable default values are hard-coded as follows:
	 * - inputBrowser = ff
	 * - inputTestURL = http://oldmutual.co.za
	 * - inputUsername = Admin
	 * - inputPassword = ""
	 * 
	 * @param inputBrowser	specify either "ff" (default), "chrome", "chrome-x" or "ie"
	 * @param inputTestURL	URL to OldMutual
	 * @param inputUsername	username for login; "Admin" (default)
	 * @param inputPassword password for username for login; "" (default)
	 * @throws IOException
	 * @author akosaraju
	 */
	@BeforeClass
	@Parameters({"inputBrowser", "inputTestURL", "inputUsername", "inputPassword"})
	public static void launchBrowser(@Optional("ff") String inputBrowser,
			@Optional("http://oldmutual.co.za") String inputTestURL, 
			@Optional("Admin") String inputUsername, 
			@Optional("Password1$") String inputPassword)
					throws IOException {
		try {
			//read properties file and set local variable values (corresponding non-null values will override the input values)
			readPropertiesFileNSetLocalVars();

			//process input parameter values (if not overridden in app.properties)
			if (sBrowser.equals("") || sBrowser.equals(null)) {
				sBrowser = inputBrowser;
				System.out.println("> sBrowser parameter set to: " + sBrowser);
			}
			if (sTestSiteURL.equals("") || sTestSiteURL.equals(null)) { 
				sTestSiteURL = inputTestURL;
				System.out.println("> sTestSiteURL parameter set to: " + sTestSiteURL);
			}
			if (sUsername.equals("") || sUsername.equals(null)) { 
				sUsername = inputUsername;
				System.out.println("> sUsername parameter set to: " + sUsername);
			}
			if (sPassword.equals("") || sPassword.equals(null)) {
				sPassword = inputPassword;
				System.out.println("> sPassword parameter set to: " + sPassword);
			}


			//open the appropriate browser session
			if(sBrowser.equalsIgnoreCase("IE"))
				driver = Browser.openBrowser(sBrowser, sTestSiteURL, sIEDriverPath);
			else if(sBrowser.equalsIgnoreCase("EDGE"))
				driver = Browser.openBrowser(sBrowser, sTestSiteURL, sEdgeDriverPath);
			else if(sBrowser.equalsIgnoreCase("chrome") || sBrowser.equalsIgnoreCase("cr") || sBrowser.equalsIgnoreCase("chromeHeadless"))
				driver = Browser.openBrowser(sBrowser, sTestSiteURL, sChromeDriverPath);
			else if(sBrowser.equalsIgnoreCase("Browserstack"))
				driver = Browser.openBrowserStack(sTestSiteURL,sbStackUserName,sbStackpasskey,sbStackOS,sbStackOs_version,sbStackBrowser,sbStackBrowser_version,sbStackSelenium_version);
			else if(sBrowser.equalsIgnoreCase("chrome-x"))
				driver = Browser.openChromeBrowserWithXtension(sTestSiteURL, sChromeDriverPath, sChromeExtension);
			else if((sBrowser.equalsIgnoreCase("ff") || sBrowser.equalsIgnoreCase("firefox")) && sFfDriverPath!=null)
				driver = Browser.openBrowser(sBrowser, sTestSiteURL, sFfDriverPath);
			else if(sBrowser.equalsIgnoreCase("ff") || sBrowser.equalsIgnoreCase("firefox"))
				driver = Browser.openBrowser(sTestSiteURL);
			else
			{
				Messages.errorMsg="No browser drivers found";
				return;
			}

			//set the default wait and timeout values 
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);						
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}


	/**
	 * Reads the app.properties file from the project's working directory and assigns the values to local variables.
	 * 
	 * NOTE: If the sBrowser, sTestSiteURL, sUsername or sPassword values are non-null in the app.properties file,
	 *       these values will override any input values to the launchBrowser() method.
	 * 
	 * @author akosaraju
	 */
	public static void readPropertiesFileNSetLocalVars()
	{
		String sKey = "";
		String sValue = "";
		Properties p = new Properties();
		FileReader reader;
		try
		{
			objProperties = new HashMap<String, String>();

			reader = new FileReader("app.properties");
			p.load(reader);
			reader.close();

			//get all the keys defined in properties file
			Set<Object> keys = p.keySet();

			//get each key and value. Put them in a hash map.
			for(Object k:keys)
			{
				sKey = (String)k;
				sValue =  p.getProperty(sKey);
				objProperties.put(sKey, sValue);
			}

			// these variables are not required but still keeping them as they are very commonly used one.
			//If you define any new property in app.properties, that can be directly taken from the BaseTest.objProperties.get("property");
			sTestSiteURL = p.getProperty("test_url");
			sUsername = p.getProperty("username");
			sPassword = p.getProperty("password");
			sBrowser = p.getProperty("browser");
			sLanguage = p.getProperty("language");

			sProjectName = p.getProperty("project_name");
			sBuild = p.getProperty("build");
			sDBPlatForm = p.getProperty("db_platform");
			sTestCasePath = p.getProperty("testcase_path");

			sIEDriverPath = p.getProperty("iedriverpath");
			sEdgeDriverPath = p.getProperty("edgedriverpath");
			sChromeDriverPath = p.getProperty("chromedriverpath");
			sFfDriverPath = p.getProperty("ffdriverpath");
			sChromeExtension = p.getProperty("chromeextension");	

			sAdminUser = p.getProperty("adminuser");
			sAdminPW = p.getProperty("adminpw");

			sTestPlanLogging = p.getProperty("logging");

			//browserStack members
			sbStackUserName=p.getProperty("bStackUserName");
			sbStackpasskey=p.getProperty("bStackpasskey");
			sbStackOS=p.getProperty("bStackOS");
			sbStackOs_version=p.getProperty("bStackOs_version");
			sbStackBrowser=p.getProperty("bStackBrowser");
			sbStackBrowser_version=p.getProperty("bStackBrowser_version");
			sbStackSelenium_version=p.getProperty("bStackSelenium_version");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Closes the WebDriver browser after the test suite is complete.
	 * 
	 * @author akosaraju
	 * @throws IOException 
	 */
	@AfterClass(alwaysRun = true)
	public static void closeBrowser() throws IOException {
		String methodID = "closeBrowser";
		if(sBrowser.equalsIgnoreCase("browserstack")) {
			driver.quit();
			System.out.println("Browserstack closed successfully");
		}
		else
		{
			driver.close();
			if (!sBrowser.equalsIgnoreCase("ff") && !sBrowser.equalsIgnoreCase("firefox")) { 
				Browser.closeCurrentBrowser(driver);
			}
			else {
				Runtime.getRuntime().exec("taskkill /T /F /IM geckodriver.exe");
			}
		}

		System.out.println(methodID + ": the WebDriver '" + sBrowser + "' browser was closed.");

		System.out.println("Test End Date & Time: " + Utilities.generateRandString());
		System.out.println("");
	}



}
