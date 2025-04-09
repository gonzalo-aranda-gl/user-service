package com.core.user_service.dto;

import com.core.user_service.api.model.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String id;

  private String name;

  private String email;

  private String password;

  private List<PhoneDto> phones;

  private String isActive;

  private LocalDateTime lastLogin;

  private LocalDateTime created;

  private LocalDateTime updated;
}
