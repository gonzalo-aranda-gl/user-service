package com.core.user_service.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.core.user_service.constants.RegularExpressions.EMAIL_FORMAT;
import static com.core.user_service.constants.RegularExpressions.PASSWORD_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {


  @NotNull(message = "The field email can't be null or empty")
  @Email(regexp = EMAIL_FORMAT, message = "The email format is invalid")
  private String email;

  @NotNull(message = "The field password can't be null or empty")
  @Pattern(regexp = PASSWORD_FORMAT, message = "The password format is invalid")
  private String password;

  private String token;

}
