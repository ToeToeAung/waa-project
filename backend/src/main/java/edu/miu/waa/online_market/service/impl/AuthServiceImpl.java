package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.dto.request.LoginRequest;
import edu.miu.waa.online_market.entity.dto.response.LoginResponse;
import edu.miu.waa.online_market.entity.dto.request.RefreshTokenRequest;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.util.JwtUtil;
import edu.miu.waa.online_market.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final LoggerService loggerService;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication result = null;
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            loggerService.logOperation("Login attempt failed for " + loginRequest.getUsername() + " * " +loginRequest.getPassword() + " * Bad credentials");
            throw new BadCredentialsException("Invalid email or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(result.getName());
        loggerService.logOperation("userDetails " + userDetails.getUsername() +userDetails.getPassword()  +" User Role ");

        final String accessToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.doGenerateRefreshToken(loginRequest.getUsername());

        var loginResponse = new LoginResponse(accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        boolean isRefreshTokenValid = jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
        if (isRefreshTokenValid) {
            var isAccessTokenExpired = jwtUtil.isTokenExpired(refreshTokenRequest.getAccessToken());
            if(isAccessTokenExpired)
                System.out.println("ACCESS TOKEN IS EXPIRED");
            else
                System.out.println("ACCESS TOKEN IS NOT EXPIRED");

            final String accessToken = jwtUtil.doGenerateRefreshToken(jwtUtil.getSubject(refreshTokenRequest.getRefreshToken()));
            var loginResponse = new LoginResponse(accessToken, refreshTokenRequest.getRefreshToken());
            return loginResponse;
        }
        return new LoginResponse();
    }
}
