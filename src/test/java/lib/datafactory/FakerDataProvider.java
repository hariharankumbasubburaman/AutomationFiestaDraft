package lib.datafactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.javafaker.Faker;

public class FakerDataProvider implements MyDataProvider {

	private final Faker faker = new Faker();

	@Override
	public Map<String, String> getTestData(Map<String, String> map) {

		
		Map<String, String> outputMap = new LinkedHashMap();

		
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String country = faker.address().country();
		String city = faker.address().city();
		String phoneNumber = faker.phoneNumber().phoneNumber();
		String randomString = faker.lorem().characters(10);

		outputMap.put("FirstName", firstName);
		outputMap.put("LastName", lastName);
		outputMap.put("Country", country);
		outputMap.put("city", city);
		outputMap.put("phoneNumber", phoneNumber);
		outputMap.put("RANDOMSTRING", randomString);

		return outputMap;
	}
}
