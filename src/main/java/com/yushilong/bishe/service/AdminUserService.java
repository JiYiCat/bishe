package com.yushilong.bishe.service;

import com.yushilong.bishe.dto.admin.AdminUserUpdateRequest;
import com.yushilong.bishe.vo.admin.AdminUserListItem;
import com.yushilong.bishe.vo.admin.UserBehaviorStatItem;

import java.util.List;

public interface AdminUserService {

    List<AdminUserListItem> listUsers();

    void updateUser(Long userId, AdminUserUpdateRequest request);

    void deleteUser(Long userId);

    List<UserBehaviorStatItem> userBehaviorStats(Long userId);
}