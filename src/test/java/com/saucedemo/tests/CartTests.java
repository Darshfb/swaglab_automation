package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Cart Management")
public class CartTests extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        inventoryPage = new LoginPage().loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Verify user can add products to cart")
    @Story("Add to Cart")
    @Description("Add multiple products to cart and verify cart badge count and items in cart")
    public void testAddProductsToCart() {
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        
        CartPage cartPage = inventoryPage
                .addProductToCart(product1)
                .addProductToCart(product2)
                .goToCart();
                
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should show 2 items")
                .isEqualTo(2);
                
        List<String> cartItems = cartPage.getCartItemNames();
        assertThat(cartItems)
                .as("Cart should contain the added products")
                .containsExactlyInAnyOrder(product1, product2);
    }

    @Test(description = "Verify user can remove product from cart page")
    @Story("Remove from Cart")
    @Description("Add a product, go to cart, remove it, and verify cart is empty")
    public void testRemoveProductFromCartPage() {
        String product = "Sauce Labs Backpack";
        
        CartPage cartPage = inventoryPage
                .addProductToCart(product)
                .goToCart()
                .removeProduct(product);
                
        assertThat(cartPage.getCartItemsCount())
                .as("Cart should be empty after removing the product")
                .isEqualTo(0);
                
        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should be empty or 0")
                .isEqualTo(0);
    }
}
