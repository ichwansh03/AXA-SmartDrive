package com.app.smartdrive.api.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserServiceImpl userServiceImpl;

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userServiceImpl.getAll();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long id) {
    return userServiceImpl.getById(id);
  }

  @PostMapping
  public ResponseEntity<?> addUser(@ModelAttribute CreateUserDto userPost) {
    User userSaved = userServiceImpl.create(userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateProfile(@ModelAttribute CreateUserDto userPost,
      @PathVariable("id") Long id) {

    User userUpdated = userServiceImpl.save(userPost, id);
    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }

}
