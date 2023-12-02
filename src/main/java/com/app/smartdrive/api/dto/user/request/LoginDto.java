package com.app.smartdrive.api.dto.user.request;

import lombok.Data;

@Data
public class LoginDto {
  private String iden;
  private String password;
}
