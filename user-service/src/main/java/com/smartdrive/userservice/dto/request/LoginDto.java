package com.smartdrive.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
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
