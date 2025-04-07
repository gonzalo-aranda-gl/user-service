package com.core.user_service.mapper;

import com.core.user_service.api.model.Phone;
import com.core.user_service.api.model.User;
import com.core.user_service.dto.PhoneDto;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", expression = "java(generateId())")
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  User mapUserRequestToEntity(SignUpRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "created", source = "created")
  @Mapping(target = "lastLogin", ignore = true)
  @Mapping(target = "token", ignore = true)
  @Mapping(target = "isActive", ignore = true)
  UserResponse mapUserEntityToUserResponse(User request);

  @Mapping(target = "id", expression = "java(generateId())")
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  Phone mapPhoneDtoToEntity(PhoneDto phoneDto);

  default String generateId() {
    return UUID.randomUUID().toString();
  }
}
