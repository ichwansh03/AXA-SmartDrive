package com.app.smartdrive.api.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
  @NotBlank(message = "Identity may not be blank")
  private String identity;
  @NotBlank(message = "Password may not be blank")
  private String password;
}
