package com.hyfata.file.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FileUtil {
    public static void writeFile(String targetPath, String content) throws IOException {
        Path path = Paths.get(targetPath);
        Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
    }

    public static String readFromFile(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return readFromInputStream(inputStream);
        }
    }

    public static String readFromFile(String path, int fromLine) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return readFromInputStream(inputStream, fromLine);
        }
    }

    public static String readFromInputStream(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");

        return scanner.hasNext() ? scanner.next() : "";
    }

    public static String readFromInputStream(InputStream inputStream, int fromLine) throws IOException {
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

        return stringBuilder.toString();
    }
}
