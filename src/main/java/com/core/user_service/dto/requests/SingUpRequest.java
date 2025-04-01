package com.core.user_service.dto.requests;

import com.core.user_service.dto.PhoneDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {

  private String name;

  @NotNull(message = "The field email can't be null or empty")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "The email format is invalid")
  private String email;

  @NotNull(message = "The field password can't be null or empty")
  private String password;

  private List<@Valid PhoneDto> phones;
}
