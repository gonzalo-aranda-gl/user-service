package com.core.user_service.api.controller;

import com.core.user_service.dto.requests.SingUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import com.core.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping(value = "/sing-up",
    produces = { "application/json" },
    consumes = { "application/json" })
  public ResponseEntity<UserResponse> singUp(@Valid @RequestBody SingUpRequest request) {
    UserResponse response = userService.singUp(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/login",
    produces = { "application/json" })
  public ResponseEntity<LoginResponse> login(@RequestParam(value = "email") String email,
                                             @RequestParam(value = "password") String password,
                                             @RequestParam(value = "token") String token) {
    LoginResponse response = userService.login();
    return ResponseEntity.ok(response);
  }

}
