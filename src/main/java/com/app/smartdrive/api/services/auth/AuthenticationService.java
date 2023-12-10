package com.app.smartdrive.api.services.auth;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.users.User;

public interface AuthenticationService {
  void signup(CreateUserDto request);
  User signin(SignInRequest request);
}
