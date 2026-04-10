package com.yushilong.bishe.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体（对应表：sys_user）
 */
@Data
public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private String role;          // USER / ADMIN
    private Integer status;       // 1=正常, 0=禁用
    private Integer loginFailCount;
    private LocalDateTime lockedUntil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}