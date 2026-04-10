package com.yushilong.bishe.service;

import com.yushilong.bishe.dto.auth.LoginRequest;
import com.yushilong.bishe.dto.auth.RegisterRequest;
import com.yushilong.bishe.vo.auth.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}