package com.yushilong.bishe.vo.video;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoHistoryItem {
    private Long videoId;
    private String originalName;
    private String status;
    private LocalDateTime uploadedAt;
    private String finalAction;
    private Double cyclingScore;
    private String topActionsJson;
}