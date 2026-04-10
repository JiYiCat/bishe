package com.yushilong.bishe.vo.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminVideoListItem {
    private Long videoId;
    private Long userId;
    private String userEmail;
    private String originalName;
    private String status;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    private String finalAction;
    private Double cyclingScore;
}