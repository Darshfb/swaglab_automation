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

    @org.testng.annotations.DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return com.saucedemo.dataproviders.JsonReader.getJsonData("invalid_users.json");
    }

    @Test(description = "Verify login fails with invalid credentials via JSON data provider", dataProvider = "invalidLoginData")
    @Story("Invalid Login")
    public void testInvalidLoginDataDriven(java.util.Map<String, String> data) {
        LoginPage loginPage = new LoginPage();
        
        String errorMessage = loginPage.enterUsername(data.get("username"))
                .enterPassword(data.get("password"))
                .clickLoginExpectingError()
                .getErrorMessage();
        
        assertThat(errorMessage)
                .as("Error message should match exactly what is defined in the JSON dataset")
                .contains(data.get("expectedErrorMessage"));
    }

    @Test(description = "Verify instant login via backend API cookie injection (UI Bypass)")
    @Story("API Authentication")
    @Description("Bypasses the login UI entirely by injecting the session cookie via RestAssured, saving 3 seconds per test")
    public void testLoginViaApiInjection() {
        // Use the utility to instantly inject the auth cookie into the browser session
        com.saucedemo.utils.ApiAuthManager.injectSessionCookie(
            com.saucedemo.core.DriverManager.getDriver(), 
            "standard_user", 
            "secret_sauce"
        );
        
        // Assert we are instantly on the protected Inventory Page without ever clicking login
        InventoryPage inventoryPage = new InventoryPage();
        assertThat(inventoryPage.isPageLoaded())
                .as("Should bypass login UI and instantly load the inventory page")
                .isTrue();
    }
}
