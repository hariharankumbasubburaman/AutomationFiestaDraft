package lib.rest;

import java.io.File;
import java.util.List;
import java.util.Map;

import common.CustomLogger;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.slf4j.ILoggerFactory;

public class RESTAssuredBase extends PreAndTest{
	private static final CustomLogger logger = CustomLogger.getInstance();
	public static RequestSpecification setLogs() {
		logger.info("Setting up RequestSpecification with logs and default content type.");
		return RestAssured
				.given()
				.log()
				.all()
				.contentType(getContentType());
	}

	public static Response get(String URL) {
		logger.info("Initiating GET request to URL: " + URL);
		Response response = setLogs().when().get(URL);
		logger.info("Response received for GET request to URL: " + URL + " with status code: " + response.statusCode());
		return response;
	}


	public static Response get() {
		logger.info("Initiating GET request with no specific URL");
		Response response = setLogs().get();
		logger.info("Response received for GET request with status code: " + response.statusCode());
		return response;
	}

	public static Response getWithHeader(Map<String, String> headers, String URL) {
		logger.info("Initiating GET request with headers to URL: " + URL);
		Response response = setLogs().headers(headers).get(URL);
		logger.info("Response received for GET request with headers. Status code: " + response.statusCode());
		return response;
	}

	public static Response post() {
		logger.info("Initiating POST request with no specific URL");
		Response response = setLogs().post();
		logger.info("Response received for POST request with status code: " + response.statusCode());
		return response;
	}

	public static Response post(String URL) {
		logger.info("Initiating POST request to URL: " + URL);
		Response response = setLogs()
				.post(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();
		logger.info("Response received for POST request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.post(URL);
	}

	public static Response postWithBodyAsFile(File bodyFile) {
		logger.info("Initiating POST request with file body");
		Response response = setLogs()
				.body(bodyFile)
				.post()
				.then()
				.log()
				.all()
				.extract()
				.response();
		logger.info("Response received for POST request with body file. Status code: " + response.statusCode());
		return setLogs()
				.body(bodyFile)
				.post();
	}
	
	public static Response postWithBodyAsFileAndUrl(File bodyFile, String URL) {
		logger.info("Initiating POST request with file body to URL: " + URL);
		Response response = setLogs().body(bodyFile).post(URL);
		logger.info("Response received for POST request with body file. Status code: " + response.statusCode());
		return response;
	}
	
	public static Response postWithHeaderAndForm(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		logger.info("Initiating POST request to URL: " + URL);
		logger.info("Headers: " + headers.toString());
		logger.info("Request Body (Form Data): " + jsonObject.toJSONString());
		Response response = setLogs()
				.headers(headers)
				.body(jsonObject)
				.post(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();
		logger.info("Response received for POST request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.headers(headers)
				.body(jsonObject)
				.post(URL);
	}

	public static Response postWithJsonAsBody(String jsonObject, String URL) {
		logger.info("Initiating POST request to URL: " + URL);
		logger.info("Request Body (JSON): " + jsonObject);

		// Log request and execute
		Response response = setLogs()
				.body(jsonObject)
				.post(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();

		// Log response details
		logger.info("Response received for POST request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.body(jsonObject)
				.post(URL);
	}


	public static Response postWithHeaderAndJsonBody(Map<String, String> headers,
			String jsonObject, String URL) {
		logger.info("Initiating POST request with headers and JSON body to URL: " + URL);
		Response response = setLogs().headers(headers).body(jsonObject).post(URL);
		logger.info("Response received for POST request with headers and JSON body. Status code: " + response.statusCode());
		return response;
	}


	public static Response postWithHeaderParam(Map<String, String> headers, String URL) {
		logger.info("Initiating POST request to URL: " + URL);
		logger.info("Headers: " + headers.toString());
		Response response = setLogs()
				.when()
				.headers(headers)
				.post(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();
		logger.info("Response received for POST request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.when()
				.headers(headers)
				.post(URL);
	}
	
	public static Response delete(String URL) {
		logger.info("Initiating DELETE request to URL: " + URL);

		// Log request and execute
		Response response = setLogs()
				.when()
				.delete(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();

		// Log response details
		logger.info("Response received for DELETE request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.when()
				.delete(URL);
	}

	public static Response deleteWithHeaderAndPathParam(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		logger.info("Initiating DELETE request to URL: " + URL);
		logger.info("Headers: " + headers.toString());
		logger.info("Request Body (Path Param): " + jsonObject.toJSONString());

		// Log request and execute
		Response response = setLogs()
				.when()
				.headers(headers)
				.body(jsonObject)
				.delete(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();

		// Log response details
		logger.info("Response received for DELETE request to URL: " + URL + " with status code: " + response.statusCode());

		return setLogs()
				.when()
				.headers(headers)
				.body(jsonObject)
				.delete(URL);
	}

	public static Response deleteWithHeaderAndPathParamWithoutRequestBody(
			Map<String, String> headers, String URL) throws Exception {
		logger.info("Initiating DELETE request to URL: " + URL);
		logger.info("Headers: " + headers.toString());

		// Log request and execute
		Response response = setLogs()
				.when()
				.headers(headers)
				.delete(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();

		// Log response details
		logger.info("Response received for DELETE request to URL: " + URL + " with status code: " + response.statusCode());
		return setLogs()
				.when()
				.headers(headers)
				.delete(URL);
	}

	public static Response putWithHeaderAndBodyParam(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		logger.info("Initiating PUT request to URL: " + URL);
		logger.info("Headers: " + headers.toString());
		logger.info("Request Body: " + jsonObject.toJSONString());

		// Log request and execute
		Response response = RestAssured.given()
				.headers(headers)
				.contentType(getContentType())
				.request()
				.body(jsonObject)
				.when()
				.put(URL)
				.then()
				.log()
				.all()
				.extract()
				.response();

		// Log response details
		logger.info("Response received for PUT request to URL: " + URL + " with status code: " + response.statusCode());
		return RestAssured.given().headers(headers).contentType(getContentType()).request()
				.body(jsonObject).when().put(URL);
	}
	
	public static ValidatableResponse enableResponseLog(Response response) {
		logger.info("Enabling response log for status code: " + response.statusCode());
		return response.then().log().all();
	}

	private static ContentType getContentType() {
		logger.debug("Setting content type to JSON");
		return ContentType.JSON;
	}

	public static void verifyContentType(Response response, String type) {
		logger.info("Verifying content type. Expected: " + type + ", Actual: " + response.getContentType());
		if(response.getContentType().toLowerCase().contains(type.toLowerCase())) {
			reportRequest("The Content type "+type+" matches the expected content type", "PASS");
		}else {
			reportRequest("The Content type "+type+" does not match the expected content type "+response.getContentType(), "FAIL");	
		}
	}

	public static void verifyResponseCode(Response response, int code) {
		if(response.statusCode() == code) {
			logger.info("The status code "+code+" matches the expected code");
			reportRequest("The status code "+code+" matches the expected code", "PASS");
		}else {
			logger.error("The status code "+code+" does not match the expected code"+response.statusCode());
			reportRequest("The status code "+code+" does not match the expected code"+response.statusCode(), "FAIL");	
		}
	}
	
	public static void verifyResponseTime(Response response, long time) {
		if(response.time() <= time) {
			logger.info("The time taken "+response.time() +" is with in the expected time");
			reportRequest("The time taken "+response.time() +" with in the expected time", "PASS");
		}else {
			logger.error("The time taken "+response.time() +" is greater than expected SLA time "+time);
			reportRequest("The time taken "+response.time() +" is greater than expected SLA time "+time,"WARNING");
		}
	}

	public static void verifyContentWithKey(Response response, String key, String expVal) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			String actValue = jsonPath.get(key);
			if(actValue.equalsIgnoreCase(expVal)) {
				logger.info("The JSON response has value "+expVal+" as expected");
				reportRequest("The JSON response has value "+expVal+" as expected. ", "PASS");
			}else {
				logger.error("The JSON response :"+actValue+" does not have the value "+expVal+" as expected");
				reportRequest("The JSON response :"+actValue+" does not have the value "+expVal+" as expected. ", "FAIL");		
			}
		}
	}
	
	public static void verifyContentsWithKey(Response response, String key, String expVal) {
		logger.info("Verifying JSON content for key: " + key + " with expected value: " + expVal);
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			List<String> actValue = jsonPath.getList(key);
			if(actValue.get(0).equalsIgnoreCase(expVal)) {
				logger.info("The JSON response contains the expected value: " + expVal);
				reportRequest("The JSON response has value "+expVal+" as expected. ", "PASS");
			}else {
				logger.error("The JSON response: " + actValue + " does not have the expected value: " + expVal);
				reportRequest("The JSON response :"+actValue+" does not have the value "+expVal+" as expected. ", "FAIL");
			}
		}
	}
	
	public static List<String> getContentsWithKey(Response response, String key) {
		logger.info("Fetching list of values for key: " + key + " from the JSON response.");
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			List<String> values = jsonPath.getList(key);
			logger.info("Retrieved values for key: " + key + " -> " + values);
			return jsonPath.getList(key);
		}else {
			logger.error("The response content type is not JSON.");
			return null;
		}
	}
	
	public static String getContentWithKey(Response response, String key) {
		logger.info("Fetching value for key: " + key + " from the JSON response.");
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			String value = jsonPath.getString(key);
			logger.info("Retrieved value for key: " + key + " -> " + value);
			return (String) jsonPath.get(key);
		}else {
			logger.error("The response content type is not JSON.");
			return null;
		}
	}

}
