package com.core.user_service.dto.requests;

import com.core.user_service.dto.PhoneDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.core.user_service.constants.RegularExpressions.EMAIL_FORMAT;
import static com.core.user_service.constants.RegularExpressions.PASSWORD_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  private String name;

  @NotNull(message = "The email can't be null or empty")
  @Email(regexp = EMAIL_FORMAT, message = "The email format is invalid")
  private String email;

  @NotNull(message = "The password can't be null or empty")
  @Pattern(regexp = PASSWORD_FORMAT, message = "The password format is invalid")
  private String password;

  private List<PhoneDto> phones;
}
