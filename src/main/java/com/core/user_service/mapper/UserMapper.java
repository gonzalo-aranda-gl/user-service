package com.core.user_service.mapper;

import com.core.user_service.api.model.Phone;
import com.core.user_service.api.model.User;
import com.core.user_service.dto.PhoneDto;
import com.core.user_service.dto.requests.SignUpRequest;
import com.core.user_service.dto.responses.LoginResponse;
import com.core.user_service.dto.responses.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", expression = "java(generateId())")
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  User mapUserRequestToEntity(SignUpRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  UserResponse mapUserEntityToUserResponse(User request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", expression = "java(generateId())")
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  Phone mapPhoneDtoToEntity(PhoneDto phoneDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  PhoneDto mapPhoneEntityToDto(Phone phone);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  LoginResponse mapUserEntityToLoginResponse(User request);

  default String generateId() {
    return UUID.randomUUID().toString();
  }
}
