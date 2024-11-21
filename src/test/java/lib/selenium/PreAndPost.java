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
import common.CustomLogger;

public class PreAndPost extends WebDriverServiceImpl{
	private static final CustomLogger logger = CustomLogger.getInstance();

	public String dataSheetName;


	@BeforeSuite
	public void beforeSuite() {
		logger.info("Test Suite Initialization");
		startReport();
	}

	@BeforeClass
	public void beforeClass() {
		logger.logTestCaseStart(testCaseName);
		startTestCase(testCaseName, testDescription);
		logger.info("Test Case Initialized");
	}
	
	@Parameters({"browser", "env"})
	@BeforeMethod
	public void beforeMethod(String browser, String environment) throws FileNotFoundException, IOException {
		logger.debug("Starting test method setup");
		logger.info("Setting up environment: " + environment);
		System.setProperty("env", environment);

		ConfigUtil.loadEnvironmentProperties();
		String URL = ConfigUtil.getProperty("url");
		String username = ConfigUtil.getProperty("username");
		String password = decryptPassword(ConfigUtil.getProperty("password"));
		String resources = ConfigUtil.getProperty("resources");

		logger.info("Environment properties loaded");

		// Set up RestAssured
		RestAssured.baseURI = URL + "/" + resources + "/";
		RestAssured.authentication = RestAssured.basic(username, password);
		logger.info("RestAssured configured with base URL: " + RestAssured.baseURI);

		// Browser initialization
		logger.info("Initializing browser: " + browser);
		if (browser.equalsIgnoreCase("chrome")) {
			driver = BrowserFactory.createBrowser(BrowserType.CHROME, URL);
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = BrowserFactory.createBrowser(BrowserType.EDGE, URL);
		}

		logger.info("Browser initialized successfully: " + browser);

		// Reporting setup
		startTestModule(nodes);
		test.assignAuthor(authors);
		test.assignCategory(category);
		HTMLReporter.svcTest = test;

		logger.info("Test method setup completed");
	}

	@AfterMethod
	public void afterMethod() {
		logger.logTestCaseEnd(testCaseName);
		logger.info("Test method teardown started");
		closeActiveBrowser();
		logger.info("Active browser closed");
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
		logger.info("Test Suite execution completed");
	}

	@DataProvider(name="fetchData", indices= {0})
	public  Object[][] getData(){
		logger.info("Fetching data for the test");
		return DataInputProvider.getSheet(dataSheetName);
	}

	
	
}
