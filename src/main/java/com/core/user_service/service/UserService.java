package com.core.user_service.service;

import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;

public interface UserService {

  UserResponse signUp(SignUpRequest request, String requestId);

  LoginResponse login(LoginRequest request, String requestId);
}
