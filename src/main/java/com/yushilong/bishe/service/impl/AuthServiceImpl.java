package com.yushilong.bishe.service.impl;

import com.yushilong.bishe.common.BusinessException;
import com.yushilong.bishe.common.ResultCode;
import com.yushilong.bishe.dto.auth.LoginRequest;
import com.yushilong.bishe.dto.auth.RegisterRequest;
import com.yushilong.bishe.entity.User;
import com.yushilong.bishe.mapper.UserMapper;
import com.yushilong.bishe.service.AuthService;
import com.yushilong.bishe.vo.auth.LoginResponse;
import com.yushilong.bishe.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(RegisterRequest request) {
        User exist = userMapper.findByEmail(request.getEmail());
        if (exist != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXIST);
        }

        User user = new User();
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setStatus(1);
        user.setLoginFailCount(0);

        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByEmail(request.getEmail().trim().toLowerCase());
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException(ResultCode.FORBIDDEN, "账户已被禁用");
        }

        // 简版锁定检查（后续可扩展）
        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.TOO_MANY_REQUESTS, "账户已锁定，请稍后再试");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            int fail = (user.getLoginFailCount() == null ? 0 : user.getLoginFailCount()) + 1;
            userMapper.updateLoginFailCount(user.getId(), fail);
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }

        // 登录成功，重置失败次数
        userMapper.resetLoginFail(user.getId());

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new LoginResponse(token, user.getId(), user.getEmail(), user.getRole());
    }
}