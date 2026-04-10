package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemLog {
    private Long id;
    private String level;      // INFO/WARN/ERROR
    private String module;     // AUTH/VIDEO/ADMIN/SYSTEM...
    private String content;
    private Long operatorId;
    private LocalDateTime createdAt;
}