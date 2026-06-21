package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Inventory Management")
public class InventoryTests extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        inventoryPage = new LoginPage().loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Verify products can be sorted by Name (Z to A)")
    @Story("Sorting")
    @Description("Sort products Z-A and verify the list is correctly ordered")
    public void testSortProductsByNameZA() {
        inventoryPage.selectSortOption("Name (Z to A)");
        
        List<String> actualNames = inventoryPage.getProductNames();
        List<String> expectedNames = actualNames.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
                
        assertThat(actualNames)
                .as("Products should be sorted by name descending")
                .isEqualTo(expectedNames);
    }

    @Test(description = "Verify products can be sorted by Price (Low to High)")
    @Story("Sorting")
    @Description("Sort products by price Low-High and verify the list is correctly ordered")
    public void testSortProductsByPriceLowToHigh() {
        inventoryPage.selectSortOption("Price (low to high)");
        
        List<Double> actualPrices = inventoryPage.getProductPrices();
        List<Double> expectedPrices = actualPrices.stream()
                .sorted()
                .collect(Collectors.toList());
                
        assertThat(actualPrices)
                .as("Products should be sorted by price ascending")
                .isEqualTo(expectedPrices);
    }
}
