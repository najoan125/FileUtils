package com.hyfata.file.utils.downloader;

import com.hyfata.file.utils.downloader.progress.DownloadProgressHandler;
import com.hyfata.file.utils.downloader.progress.DownloadProgressListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class FileDownloader {
    private final String userAgent;
    private final int updateIntervalMs;

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537";
    private static final int DEFAULT_UPDATE_INTERVAL_MS = 1000;

    private String errorMessage = "";
    private DownloadErrorType lastErrorType = DownloadErrorType.NONE;

    /**
     * Constructs a FileDownloader instance with the default User-Agent and update interval.
     */
    public FileDownloader() {
        this(DEFAULT_USER_AGENT, DEFAULT_UPDATE_INTERVAL_MS);
    }

    /**
     * Constructs a FileDownloader instance with a custom User-Agent and update interval.
     *
     * @param userAgent        The HTTP User-Agent string to use for downloads.
     * @param updateIntervalMs The interval in milliseconds for progress and speed update callbacks.
     */
    public FileDownloader(String userAgent, int updateIntervalMs) {
        if (userAgent == null || userAgent.isEmpty()) {
            this.userAgent = DEFAULT_USER_AGENT;
        } else {
            this.userAgent = userAgent;
        }
        if (updateIntervalMs <= 0) {
            this.updateIntervalMs = DEFAULT_UPDATE_INTERVAL_MS;
        } else {
            this.updateIntervalMs = updateIntervalMs;
        }
    }

    /**
     * Constructs a FileDownloader instance with a default User-Agent and the custom update interval.
     * @param updateIntervalMs The interval in milliseconds for progress and speed update callbacks.
     */
    public FileDownloader(int updateIntervalMs) {
        this(DEFAULT_USER_AGENT, updateIntervalMs);
    }

    /**
     * Downloads a file from the specified URL and notifies a listener of the progress and speed.
     * getErrorMessage() will return the error message if an error occurs(returned false) during the download.
     * getLastErrorType() will return the error type.
     *
     * @param url      The URL of the file to download.
     * @param filePath The path to save the downloaded file.
     * @param listener A listener to receive download progress and speed updates (can be null).
     * @return {@code true} if the download was successful, {@code false} otherwise.
     */
    public boolean download(String url, String filePath, DownloadProgressListener listener) {
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(this.userAgent)
                .build();
        HttpGet httpGet = new HttpGet(url);

        boolean result = false;
        errorMessage = "";
        lastErrorType = DownloadErrorType.NONE;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = downloadFromHttpResponse(response, filePath, listener); // download file from response
                if (!result) {
                    setErrorMessage("No entity in response.");
                    lastErrorType = DownloadErrorType.NO_ENTITY_IN_RESPONSE;
                }
            } else {
                setErrorMessage("HTTP Error: " + response.getStatusLine().getStatusCode());
                lastErrorType = DownloadErrorType.HTTP_ERROR;
            }
        } catch (IOException e) {
            setErrorMessage("IOException occurred during download", e);
            lastErrorType = DownloadErrorType.IO_EXCEPTION;
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }

    /**
     * Downloads a file from the specified URL. No progress listener is used.
     *
     * @param url      The URL of the file to download.
     * @param filePath The path to save the downloaded file.
     * @return {@code true} if the download was successful, {@code false} otherwise.
     */
    public boolean download(String url, String filePath) {
        return download(url, filePath, null);
    }

    private boolean downloadFromHttpResponse(HttpResponse response, String filePath, DownloadProgressListener listener) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return false;
        }

        long totalFileSize = entity.getContentLength();
        DownloadProgressHandler handler = new DownloadProgressHandler(listener, totalFileSize, System.currentTimeMillis(), updateIntervalMs);

        try (InputStream inputStream = entity.getContent();
             FileOutputStream out = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                handler.onDownloading(bytesRead);
            }
            handler.afterDownloaded();
        }
        return true;
    }

    private void setErrorMessage(String message) {
        this.errorMessage = message;
        System.err.println(message);
    }

    private void setErrorMessage(String message, Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        String stackTrace = sw.toString();
        String errorMessage = String.format("%s : %s\n%s",
                message, t.getMessage(), stackTrace);
        setErrorMessage(errorMessage);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public DownloadErrorType getErrorType() {
        return lastErrorType;
    }
}