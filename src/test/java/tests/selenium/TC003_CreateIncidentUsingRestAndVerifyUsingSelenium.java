package tests.selenium;

import java.util.List;

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
import pages.selenium.ListIncidents;
import pages.selenium.LoginPage;

public class TC003_CreateIncidentUsingRestAndVerifyUsingSelenium extends PreAndPost{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {

		testCaseName = "Creating Incident (Using REST Assured) and Search Incident (Using Selenium)";
		testDescription = "Create Incident (Using REST Assured) and Search with Selenium";
		nodes = "Incident Management";
		authors = "Hari";
		category = "UI & API";
		dataSheetName = "TC003";

	}

	@Test(dataProvider = "fetchData")
	public void createIncident(String filter) {

		logger.step(1, "Request Post URL table/incident");
		Response response = RESTAssuredBase.post("table/incident");

		logger.step(2, "Verify the response code is equal to 201");
		RESTAssuredBase.verifyResponseCode(response, 201);

		logger.step(3, "Verify the Content by Specific Key");
		incidentNumber = RESTAssuredBase.getContentWithKey(response, "result.number");

		logger.info("Started Selenium - Find Incident test");
		LoginPage loginPage = new LoginPage(driver, test);
		HomePage homePage = new HomePage(driver, test);
		ListIncidents listIncidents = new ListIncidents(driver, test);

		logger.step(4, "Enter username in the username field");
		loginPage.typeUserName(ConfigUtil.getProperty("username"),"Username");

		logger.step(5, "Enter password in the password field");
		loginPage.typePassword(ConfigUtil.getProperty("password"),"Password");

		logger.step(6, "Click the login button");
		loginPage.clickLogIn("Login button");

		logger.step(7, "Click the All link in the header");
		homePage.clickAll();

		logger.step(8, "Click the Incident");
		homePage.clickIncident();

		logger.step(9, "Click the all link in the BreadCrumb");
		homePage.clickBreadCrumb_All("All link");

		logger.step(10, "Type and search the incident number");
		listIncidents.typeAndEnterSearch(incidentNumber,"Incident");

		logger.step(11, "Verify the incident number");
		listIncidents.verifyResult(incidentNumber);
	}
}





