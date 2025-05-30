package com.hyfata.file.utils.downloader.progress;

@FunctionalInterface
public interface DownloadProgressListener {
    /**
     * 다운로드 진행률이 업데이트될 때 호출됩니다.
     *
     * @param totalBytesDownloaded 현재까지 다운로드된 바이트 수
     * @param totalSize            전체 파일 크기 (알 수 없는 경우 -1)
     * @param progressPercent      현재 진행률 (0.0 ~ 100.0), totalSize가 -1이면 이 값도 의미 없음
     * @param bytesPerSecond       현재 초당 다운로드 속도 (bytes/sec)
     */
    void onProgressUpdate(long totalBytesDownloaded, long totalSize, double progressPercent, double bytesPerSecond);
}