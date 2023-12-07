package com.app.smartdrive.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
  @NotBlank(message = "Identity may not be blank")
  private String identity;
  @NotBlank(message = "Password may not be blank")
  private String password;
}
