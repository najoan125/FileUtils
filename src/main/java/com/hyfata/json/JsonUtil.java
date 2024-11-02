package com.hyfata.json;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtil {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537";

    protected static URLConnection getJsonUrlConnection(String url) throws IOException {
        URL temp = new URL(url);
        URLConnection connection = temp.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    /**
     * Changes the name of a specified key in a JSONObject while maintaining the order.
     *
     * @param jsonObject The original JSONObject.
     * @param oldKey     The key name to change.
     * @param newKey     The new key name.
     * @return A new JSONObject with the key name changed and order maintained.
     */
    public static JSONObject renameKey(JSONObject jsonObject, String oldKey, String newKey) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (String key : jsonObject.keySet()) {
            if (key.equals(oldKey)) {
                map.put(newKey, jsonObject.get(key));  // Add with the new key name
            } else {
                map.put(key, jsonObject.get(key));
            }
        }

        return new JSONObject(map);
    }
}
