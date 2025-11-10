package com.automation.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager {
    
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    
    public static AppiumDriver getDriver() {
        return driver.get();
    }
    
    public static void initializeAndroidDriver(String appiumServerURL) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "11.0");
            caps.setCapability("deviceName", "Android Emulator");
            caps.setCapability("automationName", "UiAutomator2");
            
            caps.setCapability("app", ConfigManager.getProperty("app.path"));
            caps.setCapability("appPackage", "com.swaglabsmobileapp");
            caps.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");
            
            caps.setCapability("newCommandTimeout", 300);
            caps.setCapability("noReset", false);
            caps.setCapability("adbExecTimeout", 60000);
            
            AndroidDriver androidDriver = new AndroidDriver(new URL(appiumServerURL), caps);
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            driver.set(androidDriver);
            
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Android driver: " + e.getMessage());
        }
    }
    
    public static void initializeIOSDriver(String appiumServerURL) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", "15.0");
            caps.setCapability("deviceName", "iPhone 13");
            caps.setCapability("automationName", "XCUITest");
            
            caps.setCapability("bundleId", "com.saucelabs.mydemoapp.rn");
            
            caps.setCapability("newCommandTimeout", 300);
            caps.setCapability("noReset", false);
            
            IOSDriver iosDriver = new IOSDriver(new URL(appiumServerURL), caps);
            iosDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            driver.set(iosDriver);
            
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize iOS driver: " + e.getMessage());
        }
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
    
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}