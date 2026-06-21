package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Authentication")
public class LoginTests extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    @Story("Valid Login")
    @Description("Test logging in with a standard user and verifying inventory page is displayed")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage();
        
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
        
        assertThat(inventoryPage.isPageLoaded())
                .as("Inventory page should be loaded after successful login")
                .isTrue();
    }

    @Test(description = "Verify login with locked out user")
    @Story("Invalid Login")
    public void testLockedOutUserLogin() {
        LoginPage loginPage = new LoginPage();
        
        String errorMessage = loginPage.enterUsername("locked_out_user")
                .enterPassword("secret_sauce")
                .clickLoginExpectingError()
                .getErrorMessage();
        
        assertThat(errorMessage)
                .as("Error message should indicate user is locked out")
                .contains("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test(description = "Verify login fails with invalid password")
    @Story("Invalid Login")
    public void testInvalidPasswordLogin() {
        LoginPage loginPage = new LoginPage();
        
        String errorMessage = loginPage.enterUsername("standard_user")
                .enterPassword("wrong_password")
                .clickLoginExpectingError()
                .getErrorMessage();
        
        assertThat(errorMessage)
                .as("Error message should indicate username and password do not match")
                .contains("Epic sadface: Username and password do not match any user in this service");
    }
}
