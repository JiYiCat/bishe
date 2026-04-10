package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BehaviorFeedback {
    private Long id;
    private Long videoId;
    private Long userId;
    private String predictedAction;
    private String correctedAction;
    private String comment;
    private LocalDateTime createdAt;
}