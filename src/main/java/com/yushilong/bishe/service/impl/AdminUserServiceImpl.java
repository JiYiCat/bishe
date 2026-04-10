package com.yushilong.bishe.service.impl;

import com.yushilong.bishe.common.BusinessException;
import com.yushilong.bishe.common.ResultCode;
import com.yushilong.bishe.dto.admin.AdminUserUpdateRequest;
import com.yushilong.bishe.mapper.AdminUserMapper;
import com.yushilong.bishe.service.AdminUserService;
import com.yushilong.bishe.vo.admin.AdminUserListItem;
import com.yushilong.bishe.vo.admin.UserBehaviorStatItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminUserMapper;

    @Override
    public List<AdminUserListItem> listUsers() {
        return adminUserMapper.listUsersWithVideoCount();
    }

    @Override
    public void updateUser(Long userId, AdminUserUpdateRequest request) {
        if (!"USER".equals(request.getRole()) && !"ADMIN".equals(request.getRole())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "角色仅支持 USER 或 ADMIN");
        }
        if (request.getStatus() != 0 && request.getStatus() != 1) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "状态仅支持 0 或 1");
        }

        if (adminUserMapper.existsById(userId) == 0) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        int rows = adminUserMapper.updateUser(userId, request.getRole(), request.getStatus());
        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        if (adminUserMapper.existsById(userId) == 0) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        // 先删识别结果，再删视频，再删用户
        adminUserMapper.deleteBehaviorByUserId(userId);
        adminUserMapper.deleteVideoByUserId(userId);
        int rows = adminUserMapper.deleteUserById(userId);

        if (rows <= 0) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "删除用户失败");
        }
    }

    @Override
    public List<UserBehaviorStatItem> userBehaviorStats(Long userId) {
        if (adminUserMapper.existsById(userId) == 0) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        return adminUserMapper.userBehaviorStats(userId);
    }
}