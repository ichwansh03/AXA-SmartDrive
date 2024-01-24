package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UpdateUserResponseDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
@CrossOrigin(allowCredentials = "true",allowedHeaders = {"Authorization", "Content-Type", "Origin"}, origins = "http://localhost:3006", maxAge = 2592000)
@PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.user.getUserEntityId() == #id)")
public class UserController {
  private final UserService userService;
  private final JwtService jwtService;

  @GetMapping
  @PreAuthorize("isAuthenticated() && hasAuthority('Admin')")
  public List<UserDto> getAllUsers() {
    return TransactionMapper.mapEntityListToDtoList(userService.getAll(), UserDto.class);
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long id) {
    User user = userService.getById(id);
    return TransactionMapper.mapEntityToDto(user, UserDto.class);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDto userPost,
      @PathVariable("id") Long id) {
    User user = userService.updateUser(userPost, id);
    UpdateUserResponseDto userUpdated = TransactionMapper.mapEntityToDto(user, UpdateUserResponseDto.class);
    ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
    return ResponseEntity
            .status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(userUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
    userService.getById(id);
    userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).body("User sudah dihapus");
  }

  @PutMapping("/{id}/changeEmail")
  public ResponseEntity<?> changeEmail(@PathVariable("id") Long id, @RequestBody String email){
    userService.changeEmail(id, email);
    return ResponseEntity.ok(new MessageResponse("email has changed: " + email));
  }
}
