package tests.selenium;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.datafactory.DataSourceType;
import lib.datafactory.FakerType;
import lib.datafactory.MyDataProvider;
import lib.datafactory.TestDataFactory;
import lib.selenium.PreAndPost;
import pages.selenium.LoginPage;

public class TC001_CreateIncident2 extends PreAndPost{

	@BeforeTest
	public void setValues() {

		testCaseName = "Create Incident2 (Using Selenium)";
		testDescription = "Create a new Incident";
		nodes = "Incident Management";
		authors = "Hari";
		category = "UI";
		dataSheetName = "TC002";

	}

	@Test
	public void createIncident() {
		
		Map<String,String> map = new LinkedHashMap();
		
		map.put("filePath", "./data/"+dataSheetName+".xlsx");
		
		MyDataProvider excelDataProvider = TestDataFactory.createData(DataSourceType.EXCEL);
		Map<String, String> excelTestData = excelDataProvider.getTestData(map);
		
		MyDataProvider fakerDataProvider = TestDataFactory.createData(DataSourceType.FAKER);
		Map<String, String> fakerTestData = fakerDataProvider.getTestData(null);
		
		new LoginPage(driver,test)
		.loginApp("Username and Password")
		.clickAll()
		.clickIncident()
		.clickNew("New button")
		.getIncidentNumber()
		.selectUser(excelTestData.get("User"),"User")
		.typeShortDescription(fakerTestData.get(FakerType.RANDOMSTRING.toString()),"ShortDesc")
		.clickSubmit("Submit button")
		.verifyIncidentCreation();
	}


}





