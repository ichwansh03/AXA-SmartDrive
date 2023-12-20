package com.smartdrive.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneDto {
  private UserPhoneIdDto userPhoneId;
  private String usphPhoneType;
}

