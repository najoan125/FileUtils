package com.hyfata.file.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownloader {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537";
    public static boolean download(String url, String filePath) throws IOException {
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .build();
        HttpGet httpGet = new HttpGet(url);

        boolean result = false;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    response.getEntity().writeTo(out);
                }
                result = true;
            } else {
                System.out.println("HTTP Error: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }
}
