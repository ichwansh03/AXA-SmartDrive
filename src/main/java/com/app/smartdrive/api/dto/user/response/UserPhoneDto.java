package com.app.smartdrive.api.dto.user.response;

import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.mapper.GSON;
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

  @Override
  public String toString() {
    return GSON.toJson(this);
  }
}
