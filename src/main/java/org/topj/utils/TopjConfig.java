package org.topj.utils;

import java.io.IOException;
import java.util.Properties;

public class TopjConfig {
    private static final String VERSION = "version";

    public static String getVersion() throws IOException {
        return loadProperties().getProperty(VERSION);
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(TopjConfig.class.getResourceAsStream("/topj.properties"));
        return properties;
    }
}
