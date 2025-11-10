package com.automation.tests;

import com.automation.utils.ConfigManager;
import com.automation.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    
    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(@Optional("Android") String platform) {
        System.out.println("Setting up driver for platform: " + platform);
        String appiumServerURL = ConfigManager.getAppiumServerURL();
        
        if (platform.equalsIgnoreCase("Android")) {
            DriverManager.initializeAndroidDriver(appiumServerURL);
        } else if (platform.equalsIgnoreCase("iOS")) {
            DriverManager.initializeIOSDriver(appiumServerURL);
        } else {
            throw new IllegalArgumentException("Platform not supported: " + platform);
        }
        System.out.println("Driver initialized");
    }
    
    @AfterMethod
    public void tearDown() {
        if (DriverManager.isDriverInitialized()) {
            DriverManager.quitDriver();
            System.out.println("Driver closed");
        }
    }
}