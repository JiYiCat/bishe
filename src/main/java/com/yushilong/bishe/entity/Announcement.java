package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private String lang;       // zh/en
    private Integer status;    // 1=发布, 0=草稿
    private Long createdBy;
    private LocalDateTime createdAt;
}