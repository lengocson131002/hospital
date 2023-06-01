package com.hospital.booking.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationCore {
    private static final String FILE_CONFIG = "/application.properties";
    private static Properties properties;
    private static volatile ApplicationCore instance;

    private ApplicationCore() {
        properties = new Properties();
    }

    public static ApplicationCore getInstance() {
        ApplicationCore result = instance;
        if (result == null) {
            result = new ApplicationCore();
            result.readConfig();
        }
        return result;
    }

    public String getKey(String key) {
        return properties.getProperty(key);
    }

    private void readConfig() {
        InputStream stream = null;
        try {
            stream = ApplicationCore.class.getResourceAsStream(FILE_CONFIG);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
