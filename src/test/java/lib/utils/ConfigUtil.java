package lib.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import common.CustomLogger;

public class ConfigUtil {
	
	private static Properties properties = new Properties();
    private static final CustomLogger logger = CustomLogger.getInstance();

    public static void loadEnvironmentProperties() {
        String env = System.getProperty("env", "dev"); // default to 'dev' if not specified
        
        String filePath = "src/test/resources/config_" + env + ".properties";
        
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            logger.info("Loaded configuration for environment: " + env);
        } catch (IOException e) {
            logger.error("Failed to load environment properties from " + filePath, e);
            throw new RuntimeException("Failed to load environment properties from " + filePath);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key '" + key + "' not found in the configuration file.");
        } else {
            logger.debug("Retrieved property: " + key + " = " + value);
        }
        return value;
    }

}
