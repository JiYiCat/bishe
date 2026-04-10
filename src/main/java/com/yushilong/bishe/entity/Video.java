package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Video {
    private Long id;
    private Long userId;
    private String originalName;
    private String filename;
    private String filePath;
    private Long fileSize;
    private String status; // PENDING / PROCESSING / DONE / FAILED
    private LocalDateTime uploadedAt;
    private LocalDateTime updatedAt;
}