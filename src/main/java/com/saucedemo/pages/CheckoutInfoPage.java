package com.saucedemo.pages;

import org.openqa.selenium.By;

public class CheckoutInfoPage extends BasePage {

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutInfoPage enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    public CheckoutInfoPage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    public CheckoutInfoPage enterPostalCode(String postalCode) {
        type(postalCodeInput, postalCode);
        return this;
    }

    public CheckoutInfoPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode);
                
        // Explicitly wait for React to register the value in the DOM
        wait.until(d -> driver.findElement(postalCodeInput).getAttribute("value").equals(postalCode));
        return this;
    }

    public CheckoutOverviewPage clickContinue() {
        org.openqa.selenium.WebElement element = waitForElementClickable(continueButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try { Thread.sleep(500); } catch (Exception e) {}
        new org.openqa.selenium.interactions.Actions(driver).moveToElement(element).click().perform();
        
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("checkout-step-two"));
        } catch (Exception e) {}
        return new CheckoutOverviewPage();
    }

    public CheckoutInfoPage clickContinueExpectingError() {
        click(continueButton);
        return this;
    }

    public CartPage clickCancel() {
        click(cancelButton);
        return new CartPage();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
