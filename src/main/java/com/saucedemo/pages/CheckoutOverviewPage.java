package com.saucedemo.pages;

import org.openqa.selenium.By;

public class CheckoutOverviewPage extends BasePage {

    private final By title = By.className("title");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");

    public boolean isPageLoaded() {
        try {
            return new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.textToBe(title, "Checkout: Overview"));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public CheckoutCompletePage clickFinish() {
        org.openqa.selenium.WebElement element = waitForElementClickable(finishButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try { Thread.sleep(500); } catch (Exception e) {}
        element.click();
        return new CheckoutCompletePage();
    }

    public InventoryPage clickCancel() {
        org.openqa.selenium.WebElement element = waitForElementClickable(cancelButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try { Thread.sleep(500); } catch (Exception e) {}
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        
        // Handle routing fallback to maintain Page Object boundaries
        if (driver.getCurrentUrl().contains("cart.html")) {
            new CartPage().clickContinueShopping();
        }
        return new InventoryPage();
    }

    public double getSubtotal() {
        String text = getText(subtotalLabel);
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    public double getTax() {
        String text = getText(taxLabel);
        return Double.parseDouble(text.replace("Tax: $", ""));
    }

    public double getTotal() {
        String text = getText(totalLabel);
        return Double.parseDouble(text.replace("Total: $", ""));
    }
}
