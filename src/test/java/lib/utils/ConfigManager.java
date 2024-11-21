package lib.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import common.CustomLogger;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static final CustomLogger logger = CustomLogger.getInstance();


    public static void loadConfig() {
        String environment = System.getenv("ENV");
        if (environment == null || environment.isEmpty()) {
            environment = "dev"; // default to dev
            logger.warn("Environment variable 'ENV' not set. Defaulting to 'dev'.");
        }
        loadConfig(environment);
    }

    public static void loadConfig(String environment) {
        try {
            FileInputStream fis = new FileInputStream("config/" + environment + ".properties");
            properties.load(fis);
            String configFilePath = "config/" + environment + ".properties";
            logger.info("Loaded configuration file: " + configFilePath);
        } catch (IOException e) {
            logger.error("Failed to load configuration for environment: " + environment, e);
            throw new RuntimeException("Failed to load configuration for environment: " + environment);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

