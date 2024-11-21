package tests.rest;

import java.io.File;

import common.CustomLogger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;


public class TC001_CreateIncident extends RESTAssuredBase{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {
		testCaseName = "Create a new Incident (REST)";
		testDescription = "Create a new Incident and Verify the response";
		nodes = "Incident";
		authors = "Babu";
		category = "API";
		dataFileName = "TC001";
		
		dataFileType = "JSON";
	}

	@Test(dataProvider = "fetchData", groups = "regression")
	public void createIncident(File file) {		
		
		logger.step(1, "Request Post URL table/incident");
		Response response = postWithBodyAsFileAndUrl(file,"table/incident");
				
		logger.step(2, "Verify the key in the result content");
		verifyContentWithKey(response, "result.short_description", "This is Rest Assured Automation framework - Makaia");
		
		logger.step(3, "Verify that the Content-Type is JSON");
		verifyContentType(response, "JSON");
		
		logger.step(4, "Verify the response status code is 201");
		verifyResponseCode(response, 201);	
		
		logger.step(5, "Verify the response time");
		verifyResponseTime(response, 10000);
		
	}


}





