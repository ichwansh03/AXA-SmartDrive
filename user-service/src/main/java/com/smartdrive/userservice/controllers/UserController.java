package com.smartdrive.userservice.controllers;


import com.smartdrive.userservice.dto.request.UpdateUserRequestDto;
import com.smartdrive.userservice.dto.response.UserDto;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.mapper.TransactionMapper;
import com.smartdrive.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping
//  @PreAuthorize("isAuthenticated() && hasAuthority('Admin')")
  public List<UserDto> getAllUsers() {
    return TransactionMapper.mapEntityListToDtoList(userService.getAll(), UserDto.class);
  }

  @GetMapping("/{id}")
//  @PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.getUserEntityId() == #id)")
  public UserDto getUser(@PathVariable("id") Long id) {
    User user = userService.getById(id);
    return TransactionMapper.mapEntityToDto(user, UserDto.class);
  }

  @PatchMapping("/{id}")
//  @PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.getUserEntityId() == #id)")
  public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDto userPost,
      @PathVariable("id") Long id) {
    UpdateUserRequestDto userUpdated = userService.updateUser(userPost, id);
    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }

  @DeleteMapping("/{id}")
//  @PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.getUserEntityId() == #id)")
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
    Optional<User> user = userService.getUserById(id);
      if(user.isPresent()){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User sudah dihapus");
      }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
  }
}
