package com.core.user_service.dto.requests;

import com.core.user_service.dto.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {

  private String name;

  @NotNull(message = "The field email can't be null or empty")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
    message = "The email format is invalid")
  private String email;

  @NotNull(message = "The field password can't be null or empty")
  @Pattern(regexp = "^(?=[a-z]*[A-Z][a-z]*$)(?=(.*\\\\d){1,2})[a-zA-Z\\\\d]{8,12}$",
    message = "The password format is invalid")
  private String password;

  private List<@Valid Phone> phones;
}
