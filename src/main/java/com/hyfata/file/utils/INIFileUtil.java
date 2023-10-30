package com.hyfata.file.utils;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class INIFileUtil {

    public static INIConfiguration getFromURL(String targetUrl) throws IOException, ConfigurationException {
        URL url = new URL(targetUrl);
        InputStream inputStream = url.openStream();
        return getFromInputStream(inputStream);
    }

    public static INIConfiguration getFromFile(String path) throws IOException, ConfigurationException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return getFromInputStream(inputStream);
        }
    }

    public static INIConfiguration getFromInputStream(InputStream inputStream) throws ConfigurationException, IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);

        INIConfiguration iniConfiguration = new INIConfiguration();
        iniConfiguration.read(reader);
        return iniConfiguration;
    }
}
