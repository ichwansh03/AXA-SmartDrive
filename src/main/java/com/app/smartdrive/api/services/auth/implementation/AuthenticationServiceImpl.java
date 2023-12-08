package com.app.smartdrive.api.services.auth.implementation;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.auth.response.JwtAuthenticationResponse;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JwtAuthenticationResponse signup(CreateUserDto request) {
    User user = userService.createUserCustomer(request);
    if(user.getUserPassword() != null){
      String hashedPw = passwordEncoder.encode(user.getUserPassword());
      user.setUserPassword(hashedPw);
    }
    String jwt = jwtService.generateToken(user);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

  @Override
  public JwtAuthenticationResponse signin(SignInRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    User user = userRepository.findUserByIden(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    String jwt = jwtService.generateToken(user);
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }
}
