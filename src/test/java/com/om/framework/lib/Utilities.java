package com.om.framework.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/*

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/



public class Utilities 

{
	private static Map<String, String> objMap = null;

	//New Variables shashank
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;


	/*******************************************************************************
	Function Name 					: readTestData
	Description						: read the test data from the given sheet & row in a file and store in a hash map
	Parameters						: sFilePath, sSheetName, sTestCaseName
	Usage							: bStatus = readTestData(sFilePath, sSheetName, sTestCaseName)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ********************************************************************************/
	public static Map<String, String> readTestData(String sFilePath,String sSheetName,String sTestCaseName) throws IOException
	{
		String sKey = null;
		String sValue = null;
		try
		{

			objMap = new HashMap<String, String>();	
			Workbook objWorkbook = Workbook.getWorkbook(new File(sFilePath));
			Sheet objSheet = objWorkbook.getSheet(sSheetName);
			int iRowCount = objSheet.getRows();
			int iColCount = objSheet.getColumns();
			for(int iRowCounter = 1;iRowCounter<iRowCount;iRowCounter++)
			{
				String sCurTestCaseName = objSheet.getCell(0,iRowCounter).getContents();
				if ((sCurTestCaseName.equalsIgnoreCase(sTestCaseName)))
				{		
					for(int iColCounter = 1;iColCounter<iColCount;iColCounter++)
					{
						sKey = objSheet.getCell(iColCounter,0).getContents();
						System.out.println(sKey);
						sValue = objSheet.getCell(iColCounter,iRowCounter).getContents();
						System.out.println(sValue);
						sValue = getDate(sValue);
						sValue = getTestDataUniqueValue(sValue);

						if((!sValue.equalsIgnoreCase("Null")) && (sValue.trim().length()!=0))
						{
							objMap.put(sKey,sValue);
						}
					}
					break;
				} 
			}
		}
		catch(BiffException e)
		{
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}
		return objMap;
	}

	/*******************************************************************************
	Function Name 					: readMultipleTestData
	Description						: read the multiple test data from the given sheet & row in a file and store in a hash map
	Parameters						: sFilePath, sSheetName, sTestCaseName
	Usage							: bStatus = readTestData(sFilePath, sSheetName, sTestCaseName)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	public static Map<String,Map<String,String>> readMultipleTestData(String sFilePath,String sSheetName,String sTestCaseName)throws Exception{


		Map<String,Map<String,String>> objTestData = new HashMap<String, Map<String,String>>();

		String sKey,sValue, sCurTestCaseName,sPreviousTestCaseName = "";
		int iRowNo = 1;
		Workbook objWorkbook = Workbook.getWorkbook(new File(sFilePath));
		Sheet objSheet = objWorkbook.getSheet(sSheetName);
		int iRowCount = objSheet.getRows();
		int iColCount = objSheet.getColumns();
		for(int iRowCounter=1;iRowCounter<iRowCount;iRowCounter++){

			Map<String,String> objRowData = new HashMap<String, String>();
			sCurTestCaseName = objSheet.getCell(0, iRowCounter).getContents();
			if ((sPreviousTestCaseName).length()!=0 && (sCurTestCaseName != sPreviousTestCaseName) && sTestCaseName.trim().length() > 0){
				break;
			}
			if ((sCurTestCaseName.equalsIgnoreCase(sTestCaseName)))
			{
				sPreviousTestCaseName = sCurTestCaseName;	

				for(int iColCounter = 1;iColCounter<iColCount;iColCounter++){
					sKey = ((Sheet) objSheet).getCell(iColCounter,0).getContents();
					sKey = sKey.trim();
					sValue = ((Sheet) objSheet).getCell(iColCounter,iRowCounter).getContents();
					sValue = sValue.trim();
					sValue = getDate(sValue);
					sValue = getTestDataUniqueValue(sValue);
					System.out.println(sValue);
					if((!sValue.equalsIgnoreCase(null)) && (sValue.trim().length()!=0)){
						//System.out.println(sKey+sValue);
						objRowData.put(sKey,sValue);
					}
				}
				objTestData.put("Row" + iRowNo,objRowData);

				objRowData=null;

				iRowNo = iRowNo + 1;
			}  
		}
		return objTestData;
	}

	/*******************************************************************************
	Function Name 					: getDate
	Description						: Format the given input to date format and return that value
	Parameters						: sValue
	Usage							: bStatus = readTestData(sValue)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	private static String getDate(String sValue) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dt = sValue;

		if (sValue.trim().equalsIgnoreCase("Today"))
		{
			dt = sdf.format(cal.getTime());
		}

		if (sValue.trim().contains("Today_"))
		{
			String [] arrValues = sValue.split("_");
			int iDays = Integer.parseInt(arrValues[1]);
			cal.add(Calendar.DATE, iDays);
			//cal.
			dt = sdf.format(cal.getTime());
		}
		if (sValue.trim().contains("Today#"))
		{
			String [] arrValues = sValue.split("#");
			int iDays = Integer.parseInt(arrValues[1]);
			cal.add(Calendar.DATE, -iDays);
			//cal.
			dt = sdf.format(cal.getTime());
		}
		return dt;
	}

	/*******************************************************************************
	Function Name 					: getTestDataUniqueValue
	Description						: Format the given input to unique value
	Parameters						: sValue
	Usage							: bStatus = getTestDataUniqueValue(sValue)
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 ******************************************************************************/
	private static String getTestDataUniqueValue(String sValue){
		String sTemp;
		if (sValue.toUpperCase().contains("UNIQUE")){
			sTemp = getUniqueName();
			sValue  =  sValue.replaceAll("(?i)unique", sTemp);
			System.out.println(sValue);
		}
		return sValue;
	}

	/**
	 * Returns a time-based value.
	 *  
	 * @return String
	 */
	private static String getUniqueName() 
	{
		Calendar rightNow = Calendar.getInstance();
		String sNewName =""+rightNow.get(Calendar.YEAR)
		+ (rightNow.get(Calendar.MONTH)+1)
		+ rightNow.get(Calendar.DAY_OF_MONTH)
		+ rightNow.get(Calendar.HOUR)
		+ rightNow.get(Calendar.MINUTE)
		+ rightNow.get(Calendar.SECOND)
		+ rightNow.get(Calendar.MILLISECOND);
		return sNewName;
	}

	/**
	 * Returns the host name sub-string of a given URL.
	 * 
	 * @param sURL		input URL
	 * @return String
	 */
	public static String getHostFromURL(String sURL) {
		String hostName = "";

		String[] urlStrings = sURL.split("/");
		hostName = urlStrings[1];
		return hostName;
	}


	/**
	 * Returns a generated random number whose value is between a given min and max int value.
	 * 
	 * @param minVal	min value
	 * @param maxVal	max value 
	 * @return int
	 */
	public static int generateRandNumber(int minVal, int maxVal) {

		Random rand = new Random();
		int rNumber = rand.nextInt(maxVal - minVal + 1) + minVal;

		return rNumber;
	}

	/**
	 * Generates a time-based string by providing a valid SimpleDateFormat format.
	 * 
	 * Please see http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html for examples.
	 * 
	 * @param timeFormat		valid SimpleDateFormat string (e.g. "yyyy-MM-dd")
	 * @return String
	 */
	public static String generateTimeString(String timeFormat) {
		String rString = "";		
		String FORMAT = timeFormat;		
		rString = new SimpleDateFormat(FORMAT).format(new GregorianCalendar().getTime());		
		return rString;				
	}


	/*******************************************************************************
		Function Name 					: generateRandString
		Description						: This method will return a time-based, "random" string
		Parameters						: 
		Usage							: sRandomString = generateRandString()
		Created By						: Anil Krishna Sai Kosaraju
		Created On						: 
	 ******************************************************************************/
	public static String generateRandString() {
		String rString = "";		
		String FORMAT = "MM-dd-yyyy-hh-mm-ss";		
		rString = new SimpleDateFormat(FORMAT).format(new GregorianCalendar().getTime());		
		return rString;				
	}

	/*************************************************************************************************************************
	Function Name 					: getProjectPath
	Description						: returns the current project path
	Parameters						: 
	Created By						: Anil Krishna Sai Kosaraju
	Created On						: 
	 **************************************************************************************************************************/
	public static String getProjectPath()
	{
		String sProjectPath = "";

		try
		{
			File currentDirFile = new File(".");
			sProjectPath = currentDirFile.getAbsolutePath();

			return sProjectPath.substring(0, sProjectPath.length()-1);
		}
		catch(Exception e)
		{
			Messages.errorMsg = e.toString();
		}

		return sProjectPath;
	}


	
	
	
	

	//New Methods Shashank

	public static void setExcelFile(String Path,String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);


		} catch (Exception e){

			throw (e);

		}



	}


	public static int getRowNum() {

		int count =  ExcelWSheet.getLastRowNum();
		return count;
	}




	public static String getCellData(int RowNum, int ColNum) throws Exception{

		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			Cell.setCellType(CellType.STRING);

			String CellData = Cell.getStringCellValue();

			return CellData;

		}catch (Exception e){
			System.out.println(e);

			return"";

		}

	}



	public static void closeexcel(String Path) throws IOException
	{

		try {
			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(Path);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();
		}
		catch(Exception e) {

		}

	}


	//This method is to write in the Excel cell, Row num and Col num are the parameters

	public static void setCellData(String Result,int RowNum, int ColNum) throws Exception {

		try{

			Row  = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}



		}catch(Exception e){

			throw (e);

		}

	}










}
