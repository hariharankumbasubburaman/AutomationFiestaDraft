package lib.rest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import common.CustomLogger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.RestAssured;
import lib.utils.ConfigUtil;
import lib.utils.DataInputProvider;
import lib.utils.HTMLReporter;

public class PreAndTest extends HTMLReporter{
	private static final CustomLogger logger = CustomLogger.getInstance();

	public String dataFileName, dataFileType;	
	
	
	@BeforeSuite
	public void beforeSuite() {
		logger.info("Rest Assured Test Suite Initialization");
		startReport();
	}
	
	@BeforeTest
	public void beforeTest() {
		logger.debug("Rest Assured Before Test Initialization");
	}
	
	
	@BeforeClass
	public void beforeClass() {
		logger.logTestCaseStart(testCaseName);
		startTestCase(testCaseName, testDescription);
		logger.info("Rest Assured Before Class Initialized");
	}
	
	@Parameters("env")
	@BeforeMethod
	public void beforeMethod(String environment) throws FileNotFoundException, IOException {
		//for reports
		logger.debug("Rest Assured Starting test method setup");
		logger.info("Rest Assured Setting up environment: " + environment);
		System.setProperty("env", environment); // Set the system property for environment
        ConfigUtil.loadEnvironmentProperties();
        String URL = ConfigUtil.getProperty("url");
        String username = ConfigUtil.getProperty("username");
        String password = decryptPassword(ConfigUtil.getProperty("password"));
        String resources = ConfigUtil.getProperty("resources");
		logger.info("Rest Assured Environment properties loaded");
		svcTest = startTestModule(nodes);
		svcTest.assignAuthor(authors);
		svcTest.assignCategory(category);
		
		/*Properties prop = new Properties();
		prop.load(new FileInputStream(new File("./src/test/resources/config.properties")));*/
		
		//RestAssured.baseURI = "https://"+prop.getProperty("server")+"/"+prop.getProperty("resources")+"/";
		//RestAssured.baseURI = "https://"+prop.getProperty("server")+"/"+prop.getProperty("resources")+"/";
		RestAssured.baseURI = URL+"/"+resources;
		logger.info("RestAssured configured with base URL: " + RestAssured.baseURI);
		//RestAssured.authentication = RestAssured.basic(prop.getProperty("username"), prop.getProperty("password"));
		RestAssured.authentication = RestAssured.basic(username, password);
		logger.info("Rest assured Authentication initialized");
		logger.info("Rest Assured Test method setup completed");
	}

	@AfterMethod
	public void afterMethod() {
		logger.logTestCaseEnd(testCaseName);
	}
	
	@AfterClass
	public void afterClass() {
		logger.debug("Rest Assured After class initialized");
	}
	
	@AfterTest
	public void afterTest() {
		logger.debug("Rest Assured After test initialized");
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
		logger.info("Rest Assured Test Suite execution completed");
	}

	@DataProvider(name="fetchData")
	public  Object[][] getData(){
		logger.info("Rest Assured Data setup initialized");
		if(dataFileType.equalsIgnoreCase("Excel"))
			return DataInputProvider.getSheet(dataFileName);
		else if(dataFileType.equalsIgnoreCase("JSON")){
			Object[][] data = new Object[1][1];
			data[0][0] = new File("./data/"+dataFileName+"."+dataFileType);
			System.out.println(data[0][0]);
			return data;
		}else {
			return null;
		}
	}

	@Override
	public long takeSnap() {
		return 0;
	}	

	
	
}
