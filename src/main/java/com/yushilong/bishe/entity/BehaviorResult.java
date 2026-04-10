package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BehaviorResult {
    private Long id;
    private Long videoId;
    private String finalAction;
    private Double cyclingScore;
    private String topActionsJson; // JSON字符串
    private LocalDateTime createdAt;
}