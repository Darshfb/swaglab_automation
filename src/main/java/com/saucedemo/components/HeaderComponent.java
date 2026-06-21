package com.saucedemo.components;

import com.saucedemo.pages.BasePage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import org.openqa.selenium.By;

public class HeaderComponent extends BasePage {

    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public int getCartItemCount() {
        if (isElementDisplayed(shoppingCartBadge)) {
            return Integer.parseInt(getText(shoppingCartBadge));
        }
        return 0;
    }

    public CartPage goToCart() {
        String currentUrl = driver.getCurrentUrl();
        click(shoppingCartLink);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.not(
                            org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(currentUrl)));
        } catch (Exception e) {}
        return new CartPage();
    }

    public LoginPage logout() {
        click(menuButton);
        click(logoutLink);
        return new LoginPage();
    }
}
