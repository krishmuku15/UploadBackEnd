package com.example.fileuploader.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FileDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private long fileSize;
    private LocalDateTime downloadedAt;

    public FileDetails() {}

    public FileDetails(String fileName, long fileSize, LocalDateTime downloadedAt) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.downloadedAt = downloadedAt;
    }

    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public LocalDateTime getDownloadedAt() { return downloadedAt; }
    public void setDownloadedAt(LocalDateTime downloadedAt) { this.downloadedAt = downloadedAt; }
}
