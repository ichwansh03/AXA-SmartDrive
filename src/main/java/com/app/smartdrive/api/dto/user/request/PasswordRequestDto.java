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
  @NotBlank
  String currentPassword;
  @NotBlank
  String newPassword;
  @NotBlank
  String confirmPassword;
}
