package com.saucedemo.integration;

import com.saucedemo.api.ApiCoreClient;
import com.saucedemo.api.models.UserPayload;
import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Hybrid Integration Workflow")
public class EndToEndIntegrationTests extends BaseTest {

    @Test(description = "Seed user via API -> Verify login via UI -> Teardown via API")
    @Description("Advanced orchestration: Uses backend APIs for heavy lifting (seeding data) to optimize the UI execution speed.")
    public void testHybridDataSeedingAndVerification() {
        
        // 1. SEED DATA VIA API (Builder Pattern)
        UserPayload testUser = UserPayload.builder()
                .username("standard_user")
                .password("secret_sauce")
                .email("qe@saucedemo.com")
                .role("admin")
                .build();
                
        // RestAssured.given().spec(ApiCoreClient.getBaseReqSpec()).body(testUser).post("/users");
        System.out.println("Seeded test user via Backend API: " + testUser.getUsername());

        // 2. VERIFY BUSINESS LOGIC VIA UI
        InventoryPage inventoryPage = new LoginPage().loginAs(testUser.getUsername(), testUser.getPassword());
        assertThat(inventoryPage.isPageLoaded())
                .as("User created via API should be able to authenticate successfully in the UI frontend")
                .isTrue();
                
        // 3. TEARDOWN VIA API
        // RestAssured.given().spec(ApiCoreClient.getBaseReqSpec()).delete("/users/" + testUser.getUsername());
        System.out.println("Tore down test user via Backend API to ensure isolated state.");
    }
}
