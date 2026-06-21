package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private final By title = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    private final By inventoryItemNames = By.className("inventory_item_name");
    private final By inventoryItemPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    
    // Component Composition
    private final com.saucedemo.components.HeaderComponent header = new com.saucedemo.components.HeaderComponent();

    public boolean isPageLoaded() {
        return isElementDisplayed(title) && getText(title).equalsIgnoreCase("Products");
    }

    public InventoryPage addProductToCart(String productName) {
        By addToCart = By.xpath(String.format("//div[text()='%s']/../../..//button[text()='Add to cart']", productName));
        By remove = By.xpath("//div[text()='" + productName + "']/../../..//button[text()='Remove']");
        
        int attempts = 0;
        while (attempts < 3) {
            try {
                org.openqa.selenium.WebElement element = waitForElementClickable(addToCart);
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                try { Thread.sleep(500); } catch (Exception e) {}
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
                        .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(remove));
                return this;
            } catch (org.openqa.selenium.TimeoutException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Failed to add " + productName + " to cart after 3 attempts");
    }

    public InventoryPage removeProductFromCart(String productName) {
        String xpath = String.format("//div[text()='%s']/../../..//button[text()='Remove']", productName);
        click(By.xpath(xpath));
        return this;
    }

    public int getCartItemCount() {
        return header.getCartItemCount();
    }

    public CartPage goToCart() {
        return header.goToCart();
    }

    public InventoryPage selectSortOption(String visibleText) {
        Select select = new Select(waitForElementClickable(sortDropdown));
        select.selectByVisibleText(visibleText);
        return this;
    }

    public List<String> getProductNames() {
        return findElements(inventoryItemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return findElements(inventoryItemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public LoginPage logout() {
        return header.logout();
    }
}
