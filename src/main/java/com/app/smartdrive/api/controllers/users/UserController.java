package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.LoginDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final AuthenticationService authenticationService;

  @PostMapping("/signin")
  public ResponseEntity<?> loginCustomer(@Valid @RequestBody SignInRequest login){
    return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signin(login));
  }

  @PostMapping("/emps/signin")
  public ResponseEntity<?> loginEmployee(@Valid @RequestBody LoginDto login){
    return ResponseEntity.status(HttpStatus.OK).body(userService.loginEmployee(login.getIdentity(), login.getPassword()));
  }

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllDto();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long id) {
    return userService.getByIdDto(id);
  }

  @PostMapping
  public ResponseEntity<?> addUserCustomer(@Valid @RequestBody CreateUserDto userPost){
    User userSaved = userService.createUserCustomer(userPost);
    UserDto userDto = TransactionMapper.mapEntityToDto(userSaved, UserDto.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateUser(@Valid @ModelAttribute UpdateUserRequestDto userPost,
      @PathVariable("id") Long id) {

    UpdateUserRequestDto userUpdated = userService.save(userPost, id);
    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
    Optional<User> user = userService.getUserById(id);
    if(user.isPresent()){
      userService.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("User sudah dihapus");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
    }
  @PostMapping("/{id}/changePassword")
  public ResponseEntity<?> changePassword(@PathVariable("id") Long id, @Valid @RequestBody PasswordRequestDto passwordRequestDto){
    return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(id, passwordRequestDto));
  }

}
