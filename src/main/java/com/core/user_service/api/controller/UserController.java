package com.core.user_service.api.controller;

import com.core.user_service.dto.requests.LoginRequest;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/sign-up",
    produces = { "application/json" },
    consumes = { "application/json" })
  public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request,
                                             @RequestParam(value = "requestId") String requestId) {
    UserResponse response = userService.signUp(request, requestId);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/login",
    produces = { "application/json" },
    consumes = { "application/json" })
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                             @RequestParam(value = "requestId") String requestId) {
    LoginResponse response = userService.login(request, requestId);
    return ResponseEntity.ok(response);
  }

}
