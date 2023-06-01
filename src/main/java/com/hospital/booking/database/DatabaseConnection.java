package com.hospital.booking.database;

import com.hospital.booking.utils.ApplicationSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Object mutex = new Object();
    private static volatile DatabaseConnection instance;
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private DatabaseConnection() {
        host = ApplicationSettings.getDbHost();
        port = ApplicationSettings.getDbPort();
        database = ApplicationSettings.getDbName();
        username = ApplicationSettings.getDbUser();
        password = ApplicationSettings.getDbPassword();
    }

    public static DatabaseConnection getInstance() throws SQLException {
        DatabaseConnection result = instance;
        if (instance == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new DatabaseConnection();
                }
            }
        }

        if (instance.getConnection().isClosed()) {
            synchronized (mutex) {
                result = instance;
                if (result.getConnection().isClosed()) {
                    instance = result = new DatabaseConnection();
                }
            }
        }

        return result;
    }

    private String getConnectionUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:sqlserver://")
                .append(host)
                .append(":")
                .append(port)
                .append(";databaseName=")
                .append(database)
                .append(";encrypt=true;trustServerCertificate=true;");
        return builder.toString();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(getConnectionUrl(), username, password);
            return connection;
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Runtime Fail : " + ex.getMessage());
        }
        return null;
    }
}
