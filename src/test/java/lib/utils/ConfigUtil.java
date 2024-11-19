package lib.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	
	private static Properties properties = new Properties();

    public static void loadEnvironmentProperties() {
        String env = System.getProperty("env", "dev"); // default to 'dev' if not specified
        
        String filePath = "src/test/resources/config_" + env + ".properties";
        
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            System.out.println("Loaded configuration for environment: " + env);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load environment properties from " + filePath);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
