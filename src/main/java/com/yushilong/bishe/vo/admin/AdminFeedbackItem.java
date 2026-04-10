package com.yushilong.bishe.vo.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminFeedbackItem {
    private Long id;
    private Long videoId;
    private Long userId;
    private String userEmail;
    private String predictedAction;
    private String correctedAction;
    private String comment;
    private LocalDateTime createdAt;
}