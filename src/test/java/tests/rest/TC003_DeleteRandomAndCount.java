package tests.rest;

import java.io.File;
import java.util.List;

import common.CustomLogger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;


public class TC003_DeleteRandomAndCount extends RESTAssuredBase{
	private final CustomLogger logger = CustomLogger.getInstance();
	@BeforeTest
	public void setValues() {

		testCaseName = "Delete the incident randomly (REST)";
		testDescription = "Get all incidents from the search and delete the first incident";
		nodes = "Incident";
		authors = "Babu";
		category = "REST";
		dataFileName = "TC002";
		dataFileType = "JSON";
	}

	@Test()
	public void deleteIncidentRandomly() {

		logger.step(1, "Request Post URL table/incident");
		Response response = get("table/incident");

		logger.step(2, "Get the list of contents");
		List<String> contents = getContentsWithKey(response, "result.sys_id");

		logger.step(3, "Get the count");
		int initial = contents.size();
		logger.info("The count before delete : "+initial);

		logger.step(4, "Get random number");
		int random = (int)(Math.random() * initial);
		logger.info("The random sys_id to be deleted is : "+contents.get(random));

		logger.step(5, "Delete the first incident");
		response = delete("table/incident/"+contents.get(random));

		response.prettyPrint();

		logger.step(6, "Verify the response status code is 204");
		verifyResponseCode(response, 204);

		logger.step(7, "Post the request");
		response = get("table/incident");

		logger.step(8, "Get the Incidents");
		contents = getContentsWithKey(response, "result.sys_id");

		logger.step(9, "Get the count");
		int countAfterDelete = contents.size();
		logger.info("The count after delete : "+countAfterDelete);
	}
}





