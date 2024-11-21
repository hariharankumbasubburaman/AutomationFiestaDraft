package tests.rest;

import java.io.File;
import java.util.List;

import common.CustomLogger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;


public class TC002_DeleteFirstIncident extends RESTAssuredBase{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {

		testCaseName = "Delete the first incident (REST)";
		testDescription = "Get all incidents from the search and delete the first incident";
		nodes = "Incident";
		authors = "Babu";
		category = "REST";
		dataFileName = "TC002";
		dataFileType = "JSON";
	}

	@Test()
	public void deleteIncident() {

		logger.step(1, "Request Post URL table/incident");
		Response response = get("table/incident");

		logger.step(2, "Verify that the Content-Type is JSON");
		verifyContentType(response, "JSON");
		
		logger.step(3, "Verify the response status code is 200");
		verifyResponseCode(response, 200);

		logger.step(4, "Verify the response time");
		verifyResponseTime(response, 10000);
		
		logger.step(5, " Get the Incidents in the response");
		List<String> contents = getContentsWithKey(response, "result.sys_id");

		logger.step(5, " Delete the first incident in the response");
		response = delete("table/incident/"+contents.get(0));
		logger.info("Deleted content is " + contents.get(0));
		int statusCode = response.getStatusCode();
		logger.info("Response status code: " + statusCode);

		logger.step(6, "Verify the response status code is 204");
		verifyResponseCode(response, 204);	
		
		
	}


}





