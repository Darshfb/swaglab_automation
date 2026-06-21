package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By title = By.className("title");
    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public boolean isPageLoaded() {
        return isElementDisplayed(title) && getText(title).equalsIgnoreCase("Your Cart");
    }

    public List<String> getCartItemNames() {
        return findElements(cartItemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public CartPage removeProduct(String productName) {
        String xpath = String.format("//div[text()='%s']/../..//button[text()='Remove']", productName);
        click(By.xpath(xpath));
        return this;
    }

    public int getCartItemsCount() {
        if (isElementDisplayed(cartItems)) {
            return findElements(cartItems).size();
        }
        return 0;
    }

    public CheckoutInfoPage clickCheckout() {
        org.openqa.selenium.WebElement element = waitForElementClickable(checkoutButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try { Thread.sleep(500); } catch (Exception e) {}
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("checkout-step-one"));
        } catch (Exception e) {}
        return new CheckoutInfoPage();
    }

    public InventoryPage clickContinueShopping() {
        click(continueShoppingButton);
        return new InventoryPage();
    }
}
