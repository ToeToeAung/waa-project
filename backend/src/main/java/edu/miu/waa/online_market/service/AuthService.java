package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.dto.request.LoginRequest;
import edu.miu.waa.online_market.entity.dto.response.LoginResponse;
import edu.miu.waa.online_market.entity.dto.request.RefreshTokenRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}