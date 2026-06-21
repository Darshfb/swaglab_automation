package com.saucedemo.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
