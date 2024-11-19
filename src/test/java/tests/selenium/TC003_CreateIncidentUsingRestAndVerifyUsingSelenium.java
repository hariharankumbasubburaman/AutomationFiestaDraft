package tests.selenium;

import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;
import lib.selenium.PreAndPost;
import lib.utils.ConfigUtil;
import lib.utils.HTMLReporter;
import pages.selenium.LoginPage;

public class TC003_CreateIncidentUsingRestAndVerifyUsingSelenium extends PreAndPost{

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

		// Post the request
		Response response = RESTAssuredBase.post("table/incident");

		RESTAssuredBase.verifyResponseCode(response, 201);

		//Verify the Content by Specific Key
		incidentNumber = RESTAssuredBase.getContentWithKey(response, "result.number");

		// Selenium - Find Incident		
		new LoginPage(driver,test)
		.typeUserName(ConfigUtil.getProperty("username"),"Username")
		.typePassword(ConfigUtil.getProperty("password"),"Password")
		.clickLogIn("Login button")
			.clickAll()
			.clickIncident()
			.clickBreadCrumb_All("All link")
			.typeAndEnterSearch(incidentNumber,"Incident")
			.verifyResult(incidentNumber);
	
	}


}





