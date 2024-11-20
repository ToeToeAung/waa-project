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
           // String rawPassword = "password"; // This is the password you’re testing
            //String storedHash = "$2a$10$7EqJtq98hPqEX7fNZaFWoOq5i6wZh0gZs8B/5LnklXfFw1VxnQ4uK"; // Replace with actual hash from the database
           // String storedHash ="$2a$10$vyrme2l/48ewLVL6CNvuRO0PKcs7SURhc9Dt6MxnPsQyzmX2BP0l6";
              //      encoder.encode(rawPassword);
           // loggerService.logOperation("Stored Hash Code : " + storedHash);
           // boolean matches = encoder.matches(rawPassword, storedHash);
           // loggerService.logOperation("Password matches: " + matches);

            result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            loggerService.logOperation("result " +result.getName());
            loggerService.logOperation("Login attempt successful for " + loginRequest.getUsername());

        } catch (BadCredentialsException e) {
            loggerService.logOperation("Login attempt failed for " + loginRequest.getUsername() + " * " +loginRequest.getPassword() + " * Bad credentials");
            throw new BadCredentialsException("Invalid email or password");
        }

        loggerService.logOperation("Result " + result.getName());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(result.getName());
        loggerService.logOperation("userDetails " + userDetails.getUsername() +userDetails.getPassword() );

        final String accessToken = jwtUtil.generateToken(userDetails);
        System.out.println("Access Token " +accessToken);

        loggerService.logOperation("**Access Token " +accessToken);
        final String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());
        var loginResponse = new LoginResponse(accessToken, refreshToken);

        System.out.println("Login Response" +loginResponse);
        //loggerService.logOperation("Login Response" +loginResponse);
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
            final String accessToken = jwtUtil.doGenerateToken(  jwtUtil.getSubject(refreshTokenRequest.getRefreshToken()));
            var loginResponse = new LoginResponse(accessToken, refreshTokenRequest.getRefreshToken());
            return loginResponse;
        }
        return new LoginResponse();
    }
}
