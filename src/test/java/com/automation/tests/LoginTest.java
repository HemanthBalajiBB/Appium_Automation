package com.automation.tests;
import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Simple Login functionality test
 * Tests: Launch app -> Verify launch -> Enter credentials -> Verify login success
 */
public class LoginTest extends BaseTest {
    
    @Test
    public void testLogin() {
        System.out.println("Starting Login Test...");
        
        // Step 1: Verify app launched successfully to login screen
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isPageLoaded(), "App should launch successfully to login screen");
        System.out.println("App launched successfully");
        
        // Step 2: Enter login credentials
        System.out.println("Entering login credentials...");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        System.out.println("Credentials entered");
        
        // Step 3: Click login button
        System.out.println("Clicking login button...");
        loginPage.clickLoginSubmitButton();
        System.out.println("Login button clicked");
        
        // Step 4: Verify successful login
        System.out.println("Verifying login success...");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful and navigate to products screen");
        System.out.println("Login successful - navigated to products screen");
        
        System.out.println("Login test completed successfully!");
    }
}