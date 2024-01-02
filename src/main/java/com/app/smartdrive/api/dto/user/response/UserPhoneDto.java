package com.app.smartdrive.api.dto.user.response;

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
