package com.app.smartdrive.api.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneRequestDto {
  private String usphPhoneNumber;
  private String usphPhoneType;
}
