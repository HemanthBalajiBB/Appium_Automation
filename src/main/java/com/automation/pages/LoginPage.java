package com.automation.pages;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BasePage {
    
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;
    
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='LOGIN']")
    private WebElement loginSubmitButton;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='LOGIN']")
    private WebElement loginTitle;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement productsTitle;
    
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
    
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    public void clickLoginSubmitButton() {
        loginSubmitButton.click();
    }
    
    public boolean isLoginSuccessful() {
        try {
            Thread.sleep(2000);
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}