package com.saucedemo.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiAuthManager {
    
    private static final Logger log = LoggerFactory.getLogger(ApiAuthManager.class);

    /**
     * Executes a backend API call to authenticate and directly injects the session cookie into the browser,
     * completely bypassing the UI login flow.
     */
    public static void injectSessionCookie(WebDriver driver, String username, String password) {
        log.info("Attempting to authenticate via API for user: {}", username);
        
        // Ensure browser is on the domain before injecting cookie
        driver.get("https://www.saucedemo.com/");
        
        // In a real application, you would make a POST request to the auth endpoint here.
        // Example:
        // Response response = RestAssured.given()
        //         .formParam("username", username)
        //         .formParam("password", password)
        //         .post("https://www.saucedemo.com/auth");
        // String sessionId = response.getCookie("session-id");
        
        // Since SauceDemo is a front-end only React application, authentication is actually handled
        // purely client-side via a single simple cookie!
        
        log.info("Injecting session cookie directly into the browser DOM");
        Cookie authCookie = new Cookie("session-username", username);
        driver.manage().addCookie(authCookie);
        
        // Navigate directly to the protected route
        driver.get("https://www.saucedemo.com/inventory.html");
    }
}
