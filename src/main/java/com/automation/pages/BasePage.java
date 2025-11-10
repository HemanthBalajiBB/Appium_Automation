package com.automation.pages;

import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class BasePage {
    
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    public abstract String getPageTitle();
    
    public abstract boolean isPageLoaded();
    
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
    
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    protected AppiumDriver getDriver() {
        return driver;
    }
    
    protected WebDriverWait getWait() {
        return wait;
    }
}