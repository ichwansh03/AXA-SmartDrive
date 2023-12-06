package com.app.smartdrive.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
