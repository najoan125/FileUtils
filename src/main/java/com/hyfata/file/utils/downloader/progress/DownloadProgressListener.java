package com.hyfata.file.utils.downloader.progress;

@FunctionalInterface
public interface DownloadProgressListener {
    /**
     * Called when the download progress is updated.
     *
     * @param totalBytesDownloaded The total number of bytes downloaded so far
     * @param totalSize            The total file size (or -1 if unknown)
     * @param progressPercent      The current progress percentage (0.0 to 100.0) meaningless if the totalSize is -1
     * @param bytesPerSecond       The current download speed in bytes per second (bytes/sec)
     */
    void onProgressUpdate(long totalBytesDownloaded, long totalSize, double progressPercent, double bytesPerSecond);
}