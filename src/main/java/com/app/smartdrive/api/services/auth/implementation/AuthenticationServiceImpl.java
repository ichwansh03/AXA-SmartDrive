package com.app.smartdrive.api.services.auth.implementation;

import com.app.smartdrive.api.Exceptions.AccountNonActiveException;
import com.app.smartdrive.api.Exceptions.EmailExistException;
import com.app.smartdrive.api.Exceptions.UserPhoneExistException;
import com.app.smartdrive.api.Exceptions.UsernameExistException;
import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.entities.users.AuthUser;
import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.auth.AuthenticationService;
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
  @Transactional
  public void signup(CreateUserDto request) {
    validateUsername(request.getProfile().getUserName());

    validateEmail(request.getProfile().getUserEmail());

    request.getUserPhone().forEach(
            phone -> {
              validateUserPhone(phone.getUserPhoneId().getUsphPhoneNumber());
            }
    );

    User user = userService.createUserCustomer(request, EnumUsers.RoleName.PC);
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    userRepository.save(user);
  }

  @Override
  public AuthUser signinCustomer(SignInRequest request) {
    User user = userRepository.findUserByIden(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    AuthUser authUser = new AuthUser(user);
    if(user.getUserRoles().stream().anyMatch(usro -> usro.getUsroStatus().equals("ACTIVE"))){
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      return authUser;
    }
    throw new AccountNonActiveException();
  }

  @Override
  @Transactional
  public String changePassword(Long id, PasswordRequestDto passwordRequestDto) {
    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    if(passwordEncoder.matches(passwordRequestDto.getCurrentPassword(), user.getUserPassword())){
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
    validateUsername(request.getProfile().getUserName());

    validateEmail(request.getProfile().getUserEmail());

    request.getUserPhone().forEach(
            phone -> {
              validateUserPhone(phone.getUserPhoneId().getUsphPhoneNumber());
            }
    );

    User user = userService.createAdmin(request);
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    userRepository.save(user);
    return user;
  }

  @Override
  public void validateUsername(String username){
    if(userRepository.existsByUserName(username)){
      throw new UsernameExistException();
    }
  }

  @Override
  public void validateEmail(String email){
    if(userRepository.existsByUserEmail(email)){
      throw new EmailExistException();
    };
  }

  @Override
  public void validateUserPhone(String  phoneNumber){
    if(userPhoneRepository.findPhoneNumber(phoneNumber).isPresent())
      throw new UserPhoneExistException(phoneNumber);
  }
}
