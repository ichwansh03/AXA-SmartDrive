package com.smartdrive.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDto {
  @NotBlank(message = "current Password must not be blank")
  String currentPassword;
  @NotBlank(message = "new Password must not be blank")
  String newPassword;
  @NotBlank(message = "confirm Password must not be blank")
  String confirmPassword;
}
