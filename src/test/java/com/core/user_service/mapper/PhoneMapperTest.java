package com.core.user_service.mapper;

import com.core.user_service.api.model.Phone;
import com.core.user_service.dto.PhoneDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PhoneMapperTest {

  @Autowired
  private PhoneMapper phoneMapper;

  @Test
  void mapPhoneDtoToEntityTest() {
    PhoneDto phoneDto = PhoneDto.builder().number(1123456789).cityCode(1).countryCode("XYZ").build();
    Phone phone = this.phoneMapper.mapPhoneDtoToEntity(phoneDto);
    assertNotNull(phone);
    assertEquals(phoneDto.getNumber(), phone.getNumber());
    assertEquals(phoneDto.getCityCode(), phone.getCityCode());
    assertEquals(phoneDto.getCountryCode(), phone.getCountryCode());
  }

  @Test
  void mapPhoneEntityToDtoTest() {
    Phone phone = Phone.builder().number(1123456789).cityCode(1).countryCode("XYZ").build();
    PhoneDto phoneDto = this.phoneMapper.mapPhoneEntityToDto(phone);
    assertNotNull(phone);
    assertEquals(phone.getNumber(), phoneDto.getNumber());
    assertEquals(phone.getCityCode(), phoneDto.getCityCode());
    assertEquals(phone.getCountryCode(), phoneDto.getCountryCode());
  }
}
