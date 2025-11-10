package com.automation.pages;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * Base Page class that contains common functionality for all page objects
 * Implements Page Object Model pattern for Appium mobile automation
 */
public abstract class BasePage {
    
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    
    // Constructor
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Get page title - to be implemented by child classes
     * @return String page title
     */
    public abstract String getPageTitle();
    
    /**
     * Verify if page is loaded - to be implemented by child classes
     * @return boolean true if page is loaded
     */
    public abstract boolean isPageLoaded();
    
    /**
     * Wait for page to load with retry mechanism
     */
    public void waitForPageToLoad() {
        int attempts = 0;
        while (attempts < 3) {
            if (isPageLoaded()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            attempts++;
        }
    }
    
    /**
     * Get the current driver instance
     * @return AppiumDriver current driver
     */
    protected AppiumDriver getDriver() {
        return driver;
    }
    
    /**
     * Get the WebDriverWait instance
     * @return WebDriverWait wait instance
     */
    protected WebDriverWait getWait() {
        return wait;
    }
}