package com.saucedemo.pages;

import com.saucedemo.core.ConfigManager;
import com.saucedemo.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        int timeout = Integer.parseInt(ConfigManager.getProperty("timeout"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected void click(By locator) {
        waitForElementVisible(locator);
        waitForElementClickable(locator).click();
    }

    protected void type(By locator, String text) {
        waitForElementVisible(locator);
        WebElement element = driver.findElement(locator);
        // Clear field without breaking React synthetic events
        while (!element.getAttribute("value").isEmpty()) {
            element.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
        }
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }
}
