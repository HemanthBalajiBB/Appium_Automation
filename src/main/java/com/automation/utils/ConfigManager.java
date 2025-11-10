package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Failed to load config properties: " + e.getMessage());
            setDefaultProperties();
        }
    }
    
    private static void setDefaultProperties() {
        properties.setProperty("appium.server.url", "http://127.0.0.1:4723");
        properties.setProperty("platform.name", "Android");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "20");
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static String getAppiumServerURL() {
        return getProperty("appium.server.url", "http://127.0.0.1:4723");
    }
    
    public static String getPlatformName() {
        return getProperty("platform.name", "Android");
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    public static String getDeviceName() {
        return getProperty("device.name", "Android Emulator");
    }
    
    public static String getPlatformVersion() {
        return getProperty("platform.version", "11.0");
    }
}