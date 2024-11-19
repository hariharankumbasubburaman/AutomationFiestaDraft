package tests.selenium;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.selenium.PreAndPost;
import lib.utils.ConfigUtil;
import pages.selenium.LoginPage;

public class TC001_CreateIncident extends PreAndPost{

	@BeforeTest
	public void setValues() {

		testCaseName = "Create Incident (Using Selenium)";
		testDescription = "Create a new Incident";
		nodes = "Incident Management";
		authors = "Hari";
		category = "UI";
		dataSheetName = "TC002";

	}

	@Test(dataProvider = "fetchData")
	public void createIncident(String filter, String user, String short_desc) {
		new LoginPage(driver,test)
		.typeUserName(ConfigUtil.getProperty("username"),"Username")
		.typePassword(ConfigUtil.getProperty("password"),"Password")
		.clickLogIn("Login button")
		.clickAll()
		.clickIncident()
		.clickNew("New button")
		.getIncidentNumber()
		.selectUser(user,"User")
		.typeShortDescription(short_desc,"ShortDesc")
		.clickSubmit("Submit button")
		.verifyIncidentCreation();
	}


}





