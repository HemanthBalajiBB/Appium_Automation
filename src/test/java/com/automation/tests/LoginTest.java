package com.automation.tests;

import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    @Test
    public void testLogin() {
        System.out.println("Starting login test");
        
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isPageLoaded(), "App should launch successfully to login screen");
        System.out.println("App loaded successfully");
        
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        System.out.println("Entered credentials");
        
        loginPage.clickLoginSubmitButton();
        System.out.println("Clicked login button");
        
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful and navigate to products screen");
        System.out.println("Login test passed");
    }
}