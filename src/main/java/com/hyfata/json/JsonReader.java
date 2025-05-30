package com.hyfata.json;

import com.hyfata.file.utils.FileUtil;
import com.hyfata.json.exceptions.JsonEmptyException;
import org.json.JSONObject;

import java.io.*;
import java.net.URLConnection;
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
        String json = FileUtil.readFromInputStream(inputStream, fromLine);
        if (json.isEmpty()) {
            throw new JsonEmptyException("Json URL is empty or it damaged!");
        }
        return new JSONObject(json);
    }
}