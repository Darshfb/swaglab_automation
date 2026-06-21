package com.saucedemo.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class BrowserFactory {
    public static WebDriver createDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                // chromeOptions.addArguments("--headless"); // Enable for CI
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        
        int timeout = Integer.parseInt(ConfigManager.getProperty("timeout"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();
        return driver;
    }
}
