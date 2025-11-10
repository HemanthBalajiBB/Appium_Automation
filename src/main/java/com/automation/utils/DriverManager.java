package com.automation.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Driver Manager class to handle Appium driver initialization
 * Supports both Android and iOS platforms
 */
public class DriverManager {
    
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    
    /**
     * Get current driver instance
     * @return AppiumDriver current driver
     */
    public static AppiumDriver getDriver() {
        return driver.get();
    }
    
    /**
     * Initialize Android driver with Sauce Labs Demo App
     * @param appiumServerURL String URL of Appium server
     */
    public static void initializeAndroidDriver(String appiumServerURL) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            
            // Android capabilities
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "11.0");
            caps.setCapability("deviceName", "Android Emulator");
            caps.setCapability("automationName", "UiAutomator2");
            
            // Sauce Labs Demo App capabilities
            caps.setCapability("app", ConfigManager.getProperty("app.path"));
            caps.setCapability("appPackage", "com.swaglabsmobileapp");
            caps.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");
            
            // Additional capabilities for stability
            caps.setCapability("newCommandTimeout", 300);
            caps.setCapability("noReset", false);
            caps.setCapability("adbExecTimeout", 60000); // Increase ADB timeout to 60 seconds
            
            AndroidDriver androidDriver = new AndroidDriver(new URL(appiumServerURL), caps);
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            driver.set(androidDriver);
            
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Android driver: " + e.getMessage());
        }
    }
    
    /**
     * Initialize iOS driver with Sauce Labs Demo App
     * @param appiumServerURL String URL of Appium server
     */
    public static void initializeIOSDriver(String appiumServerURL) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            
            // iOS capabilities
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", "15.0");
            caps.setCapability("deviceName", "iPhone 13");
            caps.setCapability("automationName", "XCUITest");
            
            // Sauce Labs Demo App capabilities
            caps.setCapability("bundleId", "com.saucelabs.mydemoapp.rn");
            
            // Additional capabilities for stability
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
    
    /**
     * Quit current driver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
    
    /**
     * Check if driver is initialized
     * @return boolean true if driver exists
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}