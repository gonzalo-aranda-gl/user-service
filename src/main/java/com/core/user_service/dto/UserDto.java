package com.core.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
