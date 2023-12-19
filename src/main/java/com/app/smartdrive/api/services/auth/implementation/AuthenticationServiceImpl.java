package com.app.smartdrive.api.services.auth.implementation;

import com.app.smartdrive.api.Exceptions.EmailExistException;
import com.app.smartdrive.api.Exceptions.UserPhoneExistException;
import com.app.smartdrive.api.Exceptions.UsernameExistException;
import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final UserService userService;
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
  }

  @Override
  public User signinCustomer(SignInRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    User user = userRepository.findUserByIden(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    return user;
  }

  @Override
  @Transactional
  public String changePassword(Long id, PasswordRequestDto passwordRequestDto) {
    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    if(passwordEncoder.matches(passwordRequestDto.getCurrentPassword(), user.getPassword())){
      if(passwordRequestDto.getNewPassword().equals(passwordRequestDto.getConfirmPassword())){
        user.setUserPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
        userRepository.save(user);
        return "password has been changed";
      }
      return "Confirm password must be the same as the new password";
    }
    return "Current password is wrong";
  }

  @Override
  public User createAdmin(CreateUserDto request){
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

    User user = userService.createAdmin(request);
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    userRepository.save(user);
    return user;
  }
}
