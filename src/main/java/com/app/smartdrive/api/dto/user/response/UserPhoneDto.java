package com.app.smartdrive.api.dto.user.response;

import com.app.smartdrive.api.entities.users.UserPhoneId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneDto {
  private UserPhoneIdDto userPhoneId;
  private String usphPhoneType;
}
