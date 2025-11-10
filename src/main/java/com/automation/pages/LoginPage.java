package com.automation.pages;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Login Page class - handles login functionality
 */
public class LoginPage extends BasePage {
    
    // Login form elements
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;
    
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='LOGIN']")
    private WebElement loginSubmitButton;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='LOGIN']")
    private WebElement loginTitle;
    
    // Element to verify successful login (PRODUCTS screen)
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement productsTitle;
    
    // Constructor
    public LoginPage(AppiumDriver driver) {
        super(driver);
    }
    
    @Override
    public String getPageTitle() {
        return "Login";
    }
    
    @Override
    public boolean isPageLoaded() {
        try {
            return loginTitle.isDisplayed() && usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Enter username
     */
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    /**
     * Enter password
     */
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    /**
     * Click login button
     */
    public void clickLoginSubmitButton() {
        loginSubmitButton.click();
    }
    
    /**
     * Verify if login was successful by checking if we're on the products screen
     */
    public boolean isLoginSuccessful() {
        try {
            // Wait a moment for the screen to change after login
            Thread.sleep(2000);
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}