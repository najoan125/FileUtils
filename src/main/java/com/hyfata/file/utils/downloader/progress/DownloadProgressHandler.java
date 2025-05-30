package com.hyfata.file.utils.downloader.progress;

public class DownloadProgressHandler {
    private final DownloadProgressListener listener;
    private final long totalFileSize;
    private final long updateIntervalMs;
    private long lastUpdateTime;
    private long lastDownloadedBytes = 0;
    private long totalBytesDownloaded = 0;
    private boolean isComplete = false;

    /**
     * Constructs a DownloadProgressHandler instance with the specified listener, total file size, start time, and update interval.
     * This is not needed to be called manually. It is called by the FileDownloader class.
     * @param listener The listener to receive download progress updates.
     * @param totalFileSize The total file size in bytes.
     * @param startTime The time in milliseconds when the download started.
     * @param updateIntervalMs The interval in milliseconds for progress and speed update callbacks.
     */
    public DownloadProgressHandler(DownloadProgressListener listener, long totalFileSize, long startTime, long updateIntervalMs) {
        this.listener = listener;
        this.totalFileSize = totalFileSize;
        this.lastUpdateTime = startTime;
        this.updateIntervalMs = updateIntervalMs;
    }

    /**
     * This might be called multiple times during a download.
     * @param bytesRead The number of bytes downloaded so far.
     */
    public void onDownloading(long bytesRead) {
        if (listener == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTimeSinceLastUpdate = currentTime - lastUpdateTime;

        totalBytesDownloaded += bytesRead;

        // If the update interval has passed or the download is complete
        if (elapsedTimeSinceLastUpdate >= updateIntervalMs || totalBytesDownloaded == totalFileSize) {
            double progressPercent = (totalFileSize > 0) ? (double) totalBytesDownloaded / totalFileSize * 100.0 : -1.0;
            double bytesPerSecond = calculateBytesPerSecond(totalBytesDownloaded, elapsedTimeSinceLastUpdate);

            // If download is complete, set the current speed to 0
            if (totalBytesDownloaded == totalFileSize && totalFileSize != 0) {
                progressPercent = 100.0;
                bytesPerSecond = 0;
                isComplete = true;
            }

            listener.onProgressUpdate(totalBytesDownloaded, totalFileSize, progressPercent, bytesPerSecond);

            lastUpdateTime = currentTime;
            lastDownloadedBytes = totalBytesDownloaded;
        }
    }

    /**
     * This is called after the download loop finishes.
     */
    public void afterDownloaded() {
        if (listener == null) {
            return;
        }

        // notify one last time for 100% and 0 speed
        if (totalBytesDownloaded == totalFileSize && totalFileSize != 0) {
            // This might be called if the loop finishes quickly and doesn't hit the interval
            if (!isComplete) {
                listener.onProgressUpdate(totalBytesDownloaded, totalFileSize, 100.0, 0);
            }
        } else if (totalFileSize == -1) { // If the total size is unknown, notify completion
            listener.onProgressUpdate(totalBytesDownloaded, totalFileSize, -1.0, 0);
        }
    }

    private double calculateBytesPerSecond(long downloadedBytes, long elapsedTimeSinceLastUpdate) {
        long downloadedBytesInInterval = downloadedBytes - lastDownloadedBytes;
        return (elapsedTimeSinceLastUpdate > 0) ?
                (double) downloadedBytesInInterval / (elapsedTimeSinceLastUpdate / 1000.0) : 0;
    }
}
