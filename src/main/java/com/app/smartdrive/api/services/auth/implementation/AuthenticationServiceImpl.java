package com.app.smartdrive.api.services.auth.implementation;

import com.app.smartdrive.api.Exceptions.EmailExistException;
import com.app.smartdrive.api.Exceptions.UserPhoneExistException;
import com.app.smartdrive.api.Exceptions.UsernameExistException;
import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
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
  private final UserPhoneRepository userPhoneRepository;

  @Override
  public void signup(CreateUserDto request) {
    if(userRepository.existsByUserName((request.getProfile().getUserName()))){
      throw new UsernameExistException();
    }

    if(userRepository.existsByUserEmail(request.getProfile().getUserEmail())){
      throw new EmailExistException();
    };

    request.getUserPhone().forEach(
            phone -> {
              if(userPhoneRepository.findPhoneNumber(phone.getUserPhoneId().getUsphPhoneNumber()).isPresent())
                throw new UserPhoneExistException(phone.getUserPhoneId().getUsphPhoneNumber());
            }
    );

    User user = userService.createUserCustomer(request);
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    userRepository.save(user);
    String jwt = jwtService.generateToken(user);
//    return JwtAuthenticationResponse.builder().token(jwt).build();

  }

  @Override
  public User signin(SignInRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    User user = userRepository.findUserByIden(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    userRepository.save(user);
    return user;
  }
}
