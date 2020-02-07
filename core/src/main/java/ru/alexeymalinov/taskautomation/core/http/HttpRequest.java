package ru.alexeymalinov.taskautomation.core.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    public static String getRequest(URL url) throws IOException {
        String message = "";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (InputStream in = connection.getInputStream()) {
                message = readInputStream(in);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return message;
    }

    public static String postRequest(URL url, String string, String contentType, String charsetName) throws IOException {
        String message = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            try (OutputStream out = connection.getOutputStream()) {
                writeOutputStream(out, string);
            }
            try (InputStream in = connection.getInputStream()) {
                message = readInputStream(in);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return message;
    }

    private static String readInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static void writeOutputStream(OutputStream out, String string) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(string);
        writer.flush();
    }
}
