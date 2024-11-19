package lib.selenium;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;
import lib.browserfactory.BrowserFactory;
import lib.browserfactory.BrowserType;
import lib.utils.ConfigUtil;
import lib.utils.DataInputProvider;
import lib.utils.HTMLReporter;

public class PreAndPost extends WebDriverServiceImpl{
	
	public String dataSheetName;	
	
	
	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}
	
	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);		
	}
	
	 @Parameters({"browser", "env"})
	@BeforeMethod
	public void beforeMethod(String browser,String environment) throws FileNotFoundException, IOException {
		
		System.setProperty("env", environment); // Set the system property for environment
        ConfigUtil.loadEnvironmentProperties();
        String URL = ConfigUtil.getProperty("url");

		//for reports		
		startTestModule(nodes);
		test.assignAuthor(authors);
		test.assignCategory(category);
		HTMLReporter.svcTest = test;	
		
		if(browser.equalsIgnoreCase("chrome"))
			driver = BrowserFactory.createBrowser(BrowserType.CHROME,URL);
		else if(browser.equalsIgnoreCase("edge"))
			driver = BrowserFactory.createBrowser(BrowserType.EDGE,URL);
		
		
        String username = ConfigUtil.getProperty("username");
        String password = decryptPassword(ConfigUtil.getProperty("password"));
        String resources = ConfigUtil.getProperty("resources");
		
		RestAssured.baseURI = URL+"/"+resources+"/";
		//RestAssured.authentication = RestAssured.basic(prop.getProperty("username"), prop.getProperty("password"));
		RestAssured.authentication = RestAssured.basic(username, password);
	
	}

	@AfterMethod
	public void afterMethod() {
		closeActiveBrowser();
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
	}

	@DataProvider(name="fetchData", indices= {0})
	public  Object[][] getData(){
		return DataInputProvider.getSheet(dataSheetName);		
	}	

	
	
}
