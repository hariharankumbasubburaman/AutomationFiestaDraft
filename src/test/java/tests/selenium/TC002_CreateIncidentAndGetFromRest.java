package tests.selenium;

import common.CustomLogger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;
import lib.selenium.PreAndPost;
import lib.utils.ConfigUtil;
import lib.utils.HTMLReporter;
import pages.selenium.CreateNewIncident;
import pages.selenium.HomePage;
import pages.selenium.LoginPage;

public class TC002_CreateIncidentAndGetFromRest extends PreAndPost{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {

		testCaseName = "Create Incident(Using Selenium) and Verify(Using REST Assured)";
		testDescription = "Create Incident (Using Selenium) and Verify using REST";
		nodes = "Incident Management";
		authors = "Hari";
		category = "UI & API";
		dataSheetName = "TC002";

	}

	@Test(dataProvider = "fetchData")
	public void createIncident(String filter, String user, String short_desc) {
		LoginPage loginPage = new LoginPage(driver, test);
		HomePage homePage = new HomePage(driver, test);
		CreateNewIncident createNewIncident = new CreateNewIncident(driver, test);

		logger.step(1, "Enter username in the username field");
		loginPage.typeUserName(ConfigUtil.getProperty("username"),"Username");

		logger.step(2, "Enter password in the password field");
		loginPage.typePassword(ConfigUtil.getProperty("password"),"Password");

		logger.step(3, "Click the login button");
		loginPage.clickLogIn("Login button");

		logger.step(4, "Click the All link in the header");
		homePage.clickAll();

		logger.step(5, "Click the Incident");
		homePage.clickIncident();

		logger.step(6, "Click the new button");
		homePage.clickNew("New button");

		logger.step(7, "Get the Incident number ");
		createNewIncident.getIncidentNumber();

		logger.step(8, "Select the user");
		createNewIncident.selectUser(user,"User");

		logger.step(9, "Enter short description");
		createNewIncident.typeShortDescription(short_desc,"ShortDesc");

		logger.step(10, "Click the Submit button");
		createNewIncident.clickSubmit("Submit button");

		logger.step(11, "Verify the newly created incident");
		createNewIncident.verifyIncidentCreation();

		logger.step(12, "Verify the created incident number using GET emthod");
		Response response = RESTAssuredBase.get("table/incident?number="+incidentNumber);

		logger.step(13, "Verify the 200 success response code");
		RESTAssuredBase.verifyResponseCode(response, 200);

		logger.step(14, "Verify the result content short description");
		RESTAssuredBase.verifyContentsWithKey(response, "result.short_description",short_desc);
	
	
	}


}





