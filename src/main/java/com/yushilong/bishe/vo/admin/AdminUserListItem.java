package com.yushilong.bishe.vo.admin;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员-用户列表项
 */
@Data
public class AdminUserListItem {
    private Long id;
    private String email;
    private String role;
    private Integer status;
    private Integer loginFailCount;
    private LocalDateTime lockedUntil;
    private LocalDateTime createdAt;
    private Long videoCount;
}