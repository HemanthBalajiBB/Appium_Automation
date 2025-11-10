package com.automation.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager class to handle application properties
 * Reads configuration from properties files
 */
public class ConfigManager {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Failed to load config properties: " + e.getMessage());
            // Set default values if config file is not found
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties if config file is not found
     */
    private static void setDefaultProperties() {
        properties.setProperty("appium.server.url", "http://127.0.0.1:4723");
        properties.setProperty("platform.name", "Android");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "20");
    }
    
    /**
     * Get property value by key
     * @param key String property key
     * @return String property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value by key with default value
     * @param key String property key
     * @param defaultValue String default value if key not found
     * @return String property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get Appium server URL
     * @return String Appium server URL
     */
    public static String getAppiumServerURL() {
        return getProperty("appium.server.url", "http://127.0.0.1:4723");
    }
    
    /**
     * Get platform name (Android/iOS)
     * @return String platform name
     */
    public static String getPlatformName() {
        return getProperty("platform.name", "Android");
    }
    
    /**
     * Get implicit wait timeout
     * @return int implicit wait in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Get explicit wait timeout
     * @return int explicit wait in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    /**
     * Get device name
     * @return String device name
     */
    public static String getDeviceName() {
        return getProperty("device.name", "Android Emulator");
    }
    
    /**
     * Get platform version
     * @return String platform version
     */
    public static String getPlatformVersion() {
        return getProperty("platform.version", "11.0");
    }
}