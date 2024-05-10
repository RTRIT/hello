package com.example.google_authentication.Helper;

public class AppConfig {
    private static AppConfig instance;
    private String serverAddress;
    private int serverPort;

    private AppConfig() {
        // Initialize default values or load from configuration file
        serverAddress = "172.31.99.56";
        serverPort = 9080;
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getServerAddress() {
        return serverAddress;
    }
}
