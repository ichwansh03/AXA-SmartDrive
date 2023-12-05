package com.app.smartdrive.api.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressRequestDto {
  private String usdrAddress1;
  private String usdrAddress2;
  private Long cityId;
}
