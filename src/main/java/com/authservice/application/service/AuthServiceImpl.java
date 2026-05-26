package com.authservice.application.service;

import com.authservice.application.dto.AuthResponse;
import com.authservice.application.dto.LoginRequest;
import com.authservice.application.dto.SignupRequest;
import com.authservice.common.exception.ApiServiceException;
import com.authservice.common.exception.ServiceExceptionCodes;
import com.authservice.common.response.ApiResponse;
import com.authservice.common.response.Payload;
import com.authservice.infrastructure.entity.UserEntity;
import com.authservice.infrastructure.repository.UserRepository;
import com.authservice.infrastructure.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Override
    public ApiResponse signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiServiceException(ServiceExceptionCodes.INVALID_REQUEST, "Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFullName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);

        return Payload.success(null, "Account created successfully");
    }

    @Override
    public ApiResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        UserEntity user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ApiServiceException(ServiceExceptionCodes.INVALID_CREDENTIALS));

        String token = jwtProvider.generateToken(user);
        return Payload.success(new AuthResponse(token), "Login successful");
    }
}
