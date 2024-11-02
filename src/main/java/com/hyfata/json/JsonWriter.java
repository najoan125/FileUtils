package com.hyfata.json;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonWriter {
    public static void writeToFile(JSONObject obj, String path) throws IOException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(path)), StandardCharsets.UTF_8))) {
            out.write(obj.toString());
        }
    }
}