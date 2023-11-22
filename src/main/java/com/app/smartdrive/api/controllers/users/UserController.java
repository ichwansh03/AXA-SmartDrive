package com.app.smartdrive.api.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserServiceImpl userServiceImpl;
  private final BusinessEntityService businessEntityService;
  private final UserPhoneRepository userPhoneRepository;
  private final UserRoleRepository userRoleRepository;
  private final RolesRepository rolesRepository;
  private final CityRepository cityRepository;

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userServiceImpl.getAll();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") Long id){
    return userServiceImpl.getById(id);
  }

  @PostMapping
  public ResponseEntity<?> addUser(@ModelAttribute CreateUserDto userPost){
    User userSaved = userServiceImpl.create(userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
  }
  
}
