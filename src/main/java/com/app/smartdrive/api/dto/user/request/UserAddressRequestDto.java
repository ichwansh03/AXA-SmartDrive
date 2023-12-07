package com.app.smartdrive.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressRequestDto {
  @NotBlank(message = "address 1 may not be blank")
  private String usdrAddress1;
  @NotBlank(message = "address 2 may not be blank")
  private String usdrAddress2;
  @NotNull(message = "city id may not be null")
  private Long cityId;
}
