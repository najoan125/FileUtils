package com.hyfata.json;

import com.hyfata.json.exceptions.JsonEmptyException;
import org.json.JSONObject;

import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JsonReader {
    public static JSONObject readFromURL(String url) throws IOException, JsonEmptyException {
        URLConnection connection = JsonUtil.getJsonUrlConnection(url);
        return readFromInputStream(connection.getInputStream());
    }

    public static JSONObject readFromURL(String url, int fromLine) throws IOException, JsonEmptyException {
        URLConnection connection = JsonUtil.getJsonUrlConnection(url);
        return readFromInputStream(connection.getInputStream(), fromLine);
    }

    public static JSONObject readFromFile(String path) throws IOException, JsonEmptyException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return readFromInputStream(inputStream);
        }
    }

    public static JSONObject readFromFile(String path, int fromLine) throws IOException, JsonEmptyException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return readFromInputStream(inputStream, fromLine);
        }
    }

    public static JSONObject readFromInputStream(InputStream inputStream) throws JsonEmptyException {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");

        String json = scanner.hasNext() ? scanner.next() : "";
        if (json.isEmpty()) {
            throw new JsonEmptyException("Json URL is empty or it damaged!");
        }
        return new JSONObject(json);
    }

    public static JSONObject readFromInputStream(InputStream inputStream, int fromLine) throws IOException, JsonEmptyException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        for (int i = 0; i < fromLine - 1; i++) {
            reader.readLine();
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        String json = stringBuilder.toString();
        if (json.isEmpty()) {
            throw new JsonEmptyException("Json URL is empty or it damaged!");
        }
        return new JSONObject(json);
    }
}