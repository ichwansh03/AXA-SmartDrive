package com.app.smartdrive.api.controllers.auth;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signin")
  public ResponseEntity<?> loginCustomer(@Valid @RequestBody SignInRequest login){
    return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signin(login));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUpCustomer(@Valid @RequestBody CreateUserDto request){
    return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signup(request));
  }
}
