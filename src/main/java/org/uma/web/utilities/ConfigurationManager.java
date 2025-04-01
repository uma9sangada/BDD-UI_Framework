package org.uma.web.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private static ConfigurationManager instance;
    private Properties properties;

    private ConfigurationManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties.", e);
        }
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public String getDelay() {
        return getProperty("delay");
    }

    public String getUsername() {
        return getProperty("username");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public int getMaxRetryCount() {
        String maxRetryStr = getProperty("max.retry.count");
        if (maxRetryStr == null || maxRetryStr.isEmpty()) {
            return 1;
        }
        try {
            return Integer.parseInt(maxRetryStr);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return value;
    }
}