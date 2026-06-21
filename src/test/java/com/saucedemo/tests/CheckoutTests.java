package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutInfoPage;
import com.saucedemo.pages.CheckoutOverviewPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Checkout Flow")
public class CheckoutTests extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        // Log in before every test to ensure a clean session
        inventoryPage = new LoginPage().loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Verify successful checkout process")
    @Story("E2E Checkout")
    @Description("Complete the entire checkout process and verify the success message")
    public void testSuccessfulCheckoutProcess() {
        // Execute a complete, uninterrupted end-to-end checkout flow
        CheckoutCompletePage completePage = inventoryPage
                .addProductToCart("Sauce Labs Backpack")
                .goToCart()
                .clickCheckout()
                .fillCheckoutInfo("John", "Doe", "12345")
                .clickContinue()
                .clickFinish();
                
        // Verify the order was successfully dispatched
        assertThat(completePage.getCompleteHeaderText())
                .as("Complete header should confirm order")
                .isEqualTo("Thank you for your order!");
                
        assertThat(completePage.getCompleteMessageText())
                .as("Complete message should be displayed")
                .contains("Your order has been dispatched");
    }

    @Test(description = "Verify checkout total calculation")
    @Story("Checkout Calculation")
    @Description("Verify that the item total + tax equals the total on the overview page")
    public void testCheckoutTotalCalculation() {
        // Add multiple items to the cart to verify mathematical accuracy of the checkout system
        CheckoutOverviewPage overviewPage = inventoryPage
                .addProductToCart("Sauce Labs Backpack")
                .addProductToCart("Sauce Labs Bike Light")
                .goToCart()
                .clickCheckout()
                .fillCheckoutInfo("Jane", "Doe", "90210")
                .clickContinue();
                
        // Extract the financial values from the UI
        double subtotal = overviewPage.getSubtotal();
        double tax = overviewPage.getTax();
        double total = overviewPage.getTotal();
        
        // Assert the total calculation is mathematically sound
        assertThat(total)
                .as("Total should be exactly Subtotal + Tax")
                .isEqualTo(subtotal + tax);
    }

    @Test(description = "Verify checkout process with interrupted flow (cancel and add more items)")
    @Story("Interrupt Checkout")
    @Description("Add items, go to checkout, cancel, add more items, and then complete checkout")
    public void testCheckoutWithInterruptedFlow() {
        // Phase 1: Initial item selection and checkout attempt
        CheckoutInfoPage infoPage1 = inventoryPage
                .addProductToCart("Sauce Labs Backpack")
                .goToCart()
                .clickCheckout();
                
        // Fill initial checkout information
        infoPage1.fillCheckoutInfo("Alice", "Smith", "54321");
        
        // Proceed to the overview page
        CheckoutOverviewPage overviewPage1 = infoPage1.clickContinue();
        assertThat(overviewPage1.isPageLoaded()).as("Should reach overview page 1").isTrue();
        
        // Phase 2: Interruption - Cancel the checkout process and return to inventory
        InventoryPage inventoryPage2 = overviewPage1.clickCancel();
        
        // Phase 3: Resume shopping - Add a second item to the cart
        CartPage cartPage = inventoryPage2
                .addProductToCart("Sauce Labs Bike Light")
                .goToCart();
                
        // Critical Assertion: Verify the cart retains both the original item and the newly added item
        assertThat(cartPage.getCartItemsCount())
                .as("Cart should contain exactly 2 items before the final checkout")
                .isEqualTo(2);
                
        // Phase 4: Second checkout attempt
        CheckoutInfoPage infoPage2 = cartPage.clickCheckout();
                
        // Fill new checkout information (Tests React state clearing capabilities)
        infoPage2.fillCheckoutInfo("Bob", "Jones", "12345");
        
        // Proceed to the overview page again
        CheckoutOverviewPage overviewPage2 = infoPage2.clickContinue();
        assertThat(overviewPage2.isPageLoaded())
                .as("Should reach overview page 2")
                .isTrue();
                
        // Phase 5: Finalize the interrupted order
        CheckoutCompletePage completePage = overviewPage2.clickFinish();
                
        // Verify the order was successfully completed despite the interruption
        assertThat(completePage.getCompleteHeaderText())
                .as("Complete header should confirm order after interrupted flow")
                .isEqualTo("Thank you for your order!");
    }
}
