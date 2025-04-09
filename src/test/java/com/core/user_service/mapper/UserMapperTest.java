package com.core.user_service.mapper;

import com.core.user_service.TestUtils;
import com.core.user_service.api.model.User;
import com.core.user_service.dto.UserDto;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  void mapSignUpRequestToUserEntityTest() {
    SignUpRequest request = SignUpRequest.builder()
      .name("test")
      .email("test@gmail.com")
      .password("1A2l4opaz")
      .build();
    User user = this.userMapper.mapSignUpRequestToUserEntity(request);
    assertNotNull(user);
    assertEquals(request.getName(), user.getName());
    assertEquals(request.getEmail(), user.getEmail());
    assertEquals(request.getPassword(), user.getPassword());
  }

  @Test
  void mapUserEntityToUserDtoTest() {
    User user = TestUtils.createUser();
    UserDto userDto = this.userMapper.mapUserEntityToUserDto(user);
    assertEquals(user.getId(), userDto.getId());
    assertEquals(user.getName(), userDto.getName());
    assertEquals(user.getEmail(), userDto.getEmail());
    assertEquals(user.getPassword(), userDto.getPassword());
    assertEquals(user.getCreated(), userDto.getCreated());
    assertEquals(user.getCreated(), userDto.getCreated());
    assertEquals(user.getIsActive(), userDto.getIsActive());
  }

  @Test
  void mapUserDtoToUserResponseTest() {
    UserDto userDto = TestUtils.createUserDto();
    UserResponse response = this.userMapper.mapUserDtoToUserResponse(userDto);
    assertNotNull(response);
    assertEquals(userDto.getId(), response.getId());
    assertEquals(userDto.getCreated(), response.getCreated());
    assertEquals(userDto.getIsActive(), response.getIsActive());
  }

  @Test
  void mapUserEntityToLoginResponseTest() {
    UserDto userDto = TestUtils.createUserDto();
    LoginResponse response = this.userMapper.mapUserDtoToLoginResponse(userDto);
    assertNotNull(response);
    assertEquals(userDto.getId(), response.getId());
    assertEquals(userDto.getCreated(), response.getCreated());
  }

}
