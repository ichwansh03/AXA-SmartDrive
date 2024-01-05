package com.app.smartdrive.api.dto.auth.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignInResponse {
  private Long userEntityId;
  private String userName;
  private String userEmail;
  private String accessToken;
}
