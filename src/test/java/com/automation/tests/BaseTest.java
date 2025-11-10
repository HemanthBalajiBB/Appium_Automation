package com.automation.tests;
import com.automation.utils.ConfigManager;
import com.automation.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Base Test class that contains common test setup and teardown
 * All test classes should extend this class
 */
public class BaseTest {
    
    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(@Optional("Android") String platform) {
        String appiumServerURL = ConfigManager.getAppiumServerURL();
        
        if (platform.equalsIgnoreCase("Android")) {
            DriverManager.initializeAndroidDriver(appiumServerURL);
        } else if (platform.equalsIgnoreCase("iOS")) {
            DriverManager.initializeIOSDriver(appiumServerURL);
        } else {
            throw new IllegalArgumentException("Platform not supported: " + platform);
        }
        
        System.out.println("Driver initialized for platform: " + platform);
    }
    
    @AfterMethod
    public void tearDown() {
        if (DriverManager.isDriverInitialized()) {
            DriverManager.quitDriver();
            System.out.println("Driver quit successfully");
        }
    }
}