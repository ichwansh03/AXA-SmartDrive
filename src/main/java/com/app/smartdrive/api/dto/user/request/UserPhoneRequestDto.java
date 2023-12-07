package com.app.smartdrive.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneRequestDto {
  @NotBlank(message = "phone number may not be blank")
  private String usphPhoneNumber;
  private String usphPhoneType;
}
