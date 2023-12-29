package com.app.smartdrive.api.services.auth;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.entities.users.User;
import jakarta.transaction.Transactional;

public interface AuthenticationService {
  void signup(CreateUserDto request);
  User signinCustomer(SignInRequest request);

  @Transactional
  String changePassword(Long id, PasswordRequestDto passwordRequestDto);

  User createAdmin(CreateUserDto profileRequestDto);

  void validateUsername(String username);

  void validateEmail(String email);

  void validateUserPhone(String phoneNumber);
}
