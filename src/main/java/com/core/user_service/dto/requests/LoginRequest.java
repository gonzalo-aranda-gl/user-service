package com.core.user_service.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.core.user_service.constants.RegularExpressions.EMAIL_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {


  @NotNull(message = "The email can't be null or empty")
  @Email(regexp = EMAIL_FORMAT, message = "The email format is invalid")
  private String email;

  @NotNull(message = "The password can't be null or empty")
  private String password;

  @NotNull(message = "The token can't be null or empty")
  private String token;

}
