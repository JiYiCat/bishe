package com.yushilong.bishe.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 管理员更新用户请求
 */
@Data
public class AdminUserUpdateRequest {

    @NotBlank(message = "角色不能为空")
    private String role; // USER / ADMIN

    @NotNull(message = "状态不能为空")
    private Integer status; // 1=正常,0=禁用
}