package com.yushilong.bishe.controller;

import com.yushilong.bishe.common.Result;
import com.yushilong.bishe.dto.admin.AdminUserUpdateRequest;
import com.yushilong.bishe.security.RequireAdmin;
import com.yushilong.bishe.service.AdminUserService;
import com.yushilong.bishe.vo.admin.AdminUserListItem;
import com.yushilong.bishe.vo.admin.UserBehaviorStatItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员-用户管理接口
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@RequireAdmin
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public Result<List<AdminUserListItem>> list() {
        return Result.success(adminUserService.listUsers());
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long userId,
                               @Valid @RequestBody AdminUserUpdateRequest req) {
        adminUserService.updateUser(userId, req);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long userId) {
        adminUserService.deleteUser(userId);
        return Result.success();
    }

    @GetMapping("/{id}/behavior")
    public Result<List<UserBehaviorStatItem>> behavior(@PathVariable("id") Long userId) {
        return Result.success(adminUserService.userBehaviorStats(userId));
    }
}