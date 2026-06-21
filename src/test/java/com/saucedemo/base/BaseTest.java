package com.saucedemo.base;

import com.saucedemo.core.BrowserFactory;
import com.saucedemo.core.ConfigManager;
import com.saucedemo.core.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigManager.getProperty("browser");
        WebDriver driver = BrowserFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        DriverManager.getDriver().get(ConfigManager.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
