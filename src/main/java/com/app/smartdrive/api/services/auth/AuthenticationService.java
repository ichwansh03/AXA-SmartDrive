package com.app.smartdrive.api.services.auth;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.auth.response.JwtAuthenticationResponse;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;

public interface AuthenticationService {
  JwtAuthenticationResponse signup(CreateUserDto request);
  JwtAuthenticationResponse signin(SignInRequest request);
}
