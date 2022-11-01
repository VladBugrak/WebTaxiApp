package com.taxi.util;

import java.util.ResourceBundle;

public class DatabaseManager {

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("database");

    private DatabaseManager() { }
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }


}
