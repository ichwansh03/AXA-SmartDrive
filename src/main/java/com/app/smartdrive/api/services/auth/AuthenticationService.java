package com.app.smartdrive.api.services.auth;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.entities.users.AuthUser;
import com.app.smartdrive.api.entities.users.User;

public interface AuthenticationService {
  void signup(CreateUserDto request);

  AuthUser signinCustomer(SignInRequest request);

  String changePassword(Long id, PasswordRequestDto passwordRequestDto);

  User createAdmin(CreateUserDto profileRequestDto);

  void validateUsername(String username);

  void validateEmail(String email);

  void validateUserPhone(String phoneNumber);
}
