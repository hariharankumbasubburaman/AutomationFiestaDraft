package tests.rest;

import java.io.File;

import common.CustomLogger;
import org.slf4j.ILoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;


public class TC004_GetIncidents extends RESTAssuredBase{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {
		testCaseName = "Get Existing Incident (REST)";
		testDescription = "Get all incidents and Print the first number";
		nodes = "Incident";
		authors = "Hari";
		category = "API";
		dataFileName = "TC001";
		dataFileType = "JSON";
	}

	@Test()
	public void getIncidents() {

		logger.step(1, "Request Post URL table/incident");
		Response response = get("table/incident");

		logger.step(2, "Verify that the Content-Type is JSON");
		verifyContentType(response, "JSON");

		logger.step(3, "Verify the response status code is 200");
		verifyResponseCode(response, 200);

		logger.step(4, "Verify the response time");
		verifyResponseTime(response, 10000);

		logger.step(5, "Print the first incident number");
		String number = (String) response.jsonPath().getList("result.number").get(0);
		logger.info("Number is "+number);
		reportRequest("Verified Existing Incident Number "+number, "INFO");
		
	}


}





