package com.app.smartdrive.api.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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
  public UserDto getUser(@PathVariable("id") Long id){
    return userServiceImpl.getById(id);
  }
  
}
