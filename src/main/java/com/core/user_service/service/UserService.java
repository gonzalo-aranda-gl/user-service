package com.core.user_service.service;

import com.core.user_service.dto.requests.SingUpRequest;
import com.core.user_service.dto.responses.UserResponse;

public interface UserService {

  UserResponse singUp(SingUpRequest singUpRequest);
}
