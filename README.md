# SauceDemo Automation Framework 🚀

A modern, robust, and expert-level end-to-end (E2E) test automation framework built for the [SauceDemo](https://www.saucedemo.com/) web application. 

This framework is specifically engineered to handle complex modern Single Page Application (SPA) challenges, including React asynchronous state hydration, synthetic event desynchronization, and sticky UI elements, ensuring ultra-reliable and flake-free execution.

## 🛠️ Technology Stack
* **Language:** Java 11+
* **Core Automation:** Selenium WebDriver 4.21.0
* **Testing Framework:** TestNG
* **Assertions:** AssertJ (Fluent Assertions)
* **Reporting:** Allure
* **Build Tool:** Maven
* **Design Pattern:** Fluent Page Object Model (POM)

## 📁 Project Architecture
```text
src/
├── main/java/com/saucedemo/
│   ├── base/               # Base configurations and WebDriver lifecycle
│   ├── core/               # Singleton DriverManager and utilities
│   └── pages/              # Fluent Page Objects encapsulating UI interactions
└── test/java/com/saucedemo/
    ├── base/               # BaseTest providing setup/teardown hooks
    └── tests/              # Test classes (Login, Inventory, Cart, Checkout)
```

## 🧠 Advanced Framework Features

### 1. Fluent Page Object Model
All Page Objects return instances of other pages (or themselves), allowing test cases to be written as highly readable, unbroken chains of actions:
```java
CheckoutCompletePage completePage = inventoryPage
    .addProductToCart("Sauce Labs Backpack")
    .goToCart()
    .clickCheckout()
    .fillCheckoutInfo("John", "Doe", "12345")
    .clickContinue()
    .clickFinish();
```

### 2. Zero `Thread.sleep()` Hardcoding
The framework relies 100% on dynamic, intelligent Explicit Waits. It waits exactly as long as necessary for elements to become visible, clickable, or for specific React states to hydrate, drastically reducing execution time and flakiness.

### 3. React SPA Resiliency
Built-in mechanisms specifically designed to combat React/SPA automation bugs:
* **Synthetic Event Syncing:** Custom `type()` methods that simulate physical Backspace loops to bypass React state desynchronization when clearing populated fields.
* **Asynchronous Hydration Waiters:** URL transition waiters that block DOM queries until the browser mathematically leaves the previous page, preventing false-negative `StaleElementReferenceException` during routing.
* **OS-Level Clicks:** Integration of Selenium's `Actions` class alongside Javascript scrolling to physically click elements while bypassing overlapping "Sticky Headers" without dropping React `onSubmit` events.

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 11 or higher
* Apache Maven
* Google Chrome (The framework uses WebDriverManager internally via Selenium 4's native manager)

### Running the Tests
To execute the entire test suite, simply run:
```bash
mvn clean test
```

To run a specific test class:
```bash
mvn test -Dtest=CheckoutTests
```

To run a specific test method:
```bash
mvn test -Dtest=CheckoutTests#testCheckoutWithInterruptedFlow
```

## 📊 Viewing Test Reports
This framework integrates **Allure** for stunning, comprehensive graphical test reports. 

After running the tests, generate and serve the Allure report by running:
```bash
mvn allure:serve
```
This will automatically open a local web server displaying the execution timelines, passed/failed statuses, and granular test steps.
