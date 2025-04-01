package com.core.user_service.service;

import com.core.user_service.dto.requests.SingUpRequest;
import com.core.user_service.dto.responses.SingUpResponse;

public interface UserService {

  SingUpResponse singUp(SingUpRequest singUpRequest);
}
