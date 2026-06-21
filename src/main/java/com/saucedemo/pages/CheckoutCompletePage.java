package com.saucedemo.pages;

import org.openqa.selenium.By;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public String getCompleteHeaderText() {
        return getText(completeHeader);
    }

    public String getCompleteMessageText() {
        return getText(completeText);
    }

    public InventoryPage clickBackHome() {
        click(backHomeButton);
        return new InventoryPage();
    }
}
