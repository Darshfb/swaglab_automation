package com.saucedemo.core;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {
    
    private static final Logger log = LoggerFactory.getLogger(AllureEnvironmentWriter.class);

    public static void writeEnvironmentVariables() {
        Properties properties = new Properties();
        
        properties.put("Execution OS", System.getProperty("os.name"));
        properties.put("Java Version", System.getProperty("java.version"));
        properties.put("Browser", System.getProperty("browser", ConfigManager.getProperty("browser")));
        properties.put("Target Environment", System.getProperty("env", "qa"));
        properties.put("Target URL", ConfigManager.getProperty("url"));
        
        File allureResultsDir = new File("target/allure-results");
        if (!allureResultsDir.exists()) {
            allureResultsDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(new File(allureResultsDir, "environment.properties"))) {
            properties.store(fos, "Allure Environment Properties");
            log.info("Successfully generated Allure environment properties.");
        } catch (IOException e) {
            log.error("Failed to generate Allure environment properties", e);
        }
    }
}
