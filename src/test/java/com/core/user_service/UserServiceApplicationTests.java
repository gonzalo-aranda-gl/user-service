package com.core.user_service;

import com.core.user_service.api.controller.UserController;
import com.core.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

}
