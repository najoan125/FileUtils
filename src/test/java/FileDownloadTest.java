import com.hyfata.file.utils.downloader.FileDownloader;
import com.hyfata.file.utils.downloader.progress.DownloadProgressListener;

public class FileDownloadTest {
    public static void main(String[] args) {
        String testUrl = "https://ash-speed.hetzner.com/100MB.bin";
        String testFilePath = "100MB.bin";

        System.out.println("Starting download of " + testUrl + "...");

        FileDownloader downloader = new FileDownloader(500);

        DownloadProgressListener progressListener = (downloaded, total, percent, bytesPerSec) -> {
            String speedFormatted;
            if (bytesPerSec < 1024) {
                speedFormatted = String.format("%.2f B/s", bytesPerSec);
            } else if (bytesPerSec < 1024 * 1024) {
                speedFormatted = String.format("%.2f KB/s", bytesPerSec / 1024.0);
            } else {
                speedFormatted = String.format("%.2f MB/s", bytesPerSec / (1024.0 * 1024.0));
            }

            if (total > 0) {
                System.out.printf("Progress: %.2f%% (%d / %d bytes) - Speed: %s\n", percent, downloaded, total, speedFormatted);
            } else {
                System.out.printf("Progress: %d bytes (total size unknown) - Speed: %s\n", downloaded, speedFormatted);
            }
        };

        boolean success = downloader.download(testUrl, testFilePath, progressListener);
        if (success) {
            System.out.println("File " + testFilePath + " downloaded successfully!");
        } else {
            System.out.println("File download failed.");
        }
    }
}
