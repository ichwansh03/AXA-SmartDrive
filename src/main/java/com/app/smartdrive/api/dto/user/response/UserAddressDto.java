package com.app.smartdrive.api.dto.user.response;

import com.app.smartdrive.api.mapper.GSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDto {
  private String usdrAddress1;
  private String usdrAddress2;
  private UserCityDto city;

  @Override
  public String toString() {
    return GSON.toJson(this);
  }
}