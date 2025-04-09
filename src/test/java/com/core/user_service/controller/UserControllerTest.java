package com.core.user_service.controller;

import com.core.user_service.TestFileUtils;
import com.core.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

  private static final String SIGN_UP_PATH = "/sign-up";

  private static final String LOGIN_PATH = "/login";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void signUpTest() throws Exception {
    String request = TestFileUtils.getContentFromJsonFile("requests/sign-up-ok-request.json");
    this.mockMvc.perform(
      MockMvcRequestBuilders.post(SIGN_UP_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
        .param("requestId", UUID.randomUUID().toString())
    ).andExpect(status().isOk());

    verify(this.userService, times(1))
      .signUp(any(), anyString());
  }

  @Test
  void loginTest() throws Exception {
    String request = TestFileUtils.getContentFromJsonFile("requests/login-ok-request.json");
    this.mockMvc.perform(
      MockMvcRequestBuilders.post(LOGIN_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
        .param("requestId", UUID.randomUUID().toString())
    ).andExpect(status().isOk());

    verify(this.userService, times(1))
      .login(any(), anyString());
  }

}
