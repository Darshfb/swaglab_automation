package com.saucedemo.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public InventoryPage clickLogin() {
        click(loginButton);
        return new InventoryPage();
    }
    
    public LoginPage clickLoginExpectingError() {
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public InventoryPage loginAs(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}
