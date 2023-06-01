package com.hospital.booking.utils;

public class ApplicationSettings {
    private static final ApplicationCore setting = ApplicationCore.getInstance();

    public static String getDbName() {
        return setting.getKey("DB_NAME");
    }

    public static String getDbHost() {
        return setting.getKey("DB_HOST");
    }

    public static String getDbPort() {
        return setting.getKey("DB_PORT");
    }

    public static String getDbUser() {
        return setting.getKey("DB_USER");
    }

    public static String getDbPassword() {
        return setting.getKey("DB_PWD");
    }

    public static String getGoogleClientId() {
        return setting.getKey("GOOGLE_CLIENT_ID");
    }

    public static String getGoogleClientSecret() {
        return setting.getKey("GOOGLE_CLIENT_SECRET");
    }

    public static String getGmailHost() {
        return setting.getKey("GMAIL_HOST");
    }

    public static String getGmailPort() {
        return setting.getKey("GMAIL_PORT");
    }

    public static String getGmailUsername() {
        return setting.getKey("GMAIL_USERNAME");
    }

    public static String getGmailPassword() {
        return setting.getKey("GMAIL_PASSWORD");
    }

    public static String getGmailFrom() {
        return setting.getKey("GMAIL_FROM");
    }
}
