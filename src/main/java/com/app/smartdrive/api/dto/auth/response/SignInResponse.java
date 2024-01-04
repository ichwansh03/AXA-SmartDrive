package com.app.smartdrive.api.dto.auth.response;

import lombok.Builder;

@Builder
public class SignInResponse {
  private Long userEntityId;
  private String userName;
  private String userEmail;
  private String accessToken;
}
