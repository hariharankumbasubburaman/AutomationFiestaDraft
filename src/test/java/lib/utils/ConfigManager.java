package lib.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();
    
    
    public static void loadConfig() {
        String environment = System.getenv("ENV");
        if (environment == null || environment.isEmpty()) {
            environment = "dev"; // default to dev
        }
        loadConfig(environment);
    }

    public static void loadConfig(String environment) {
        try {
            FileInputStream fis = new FileInputStream("config/" + environment + ".properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration for environment: " + environment);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

