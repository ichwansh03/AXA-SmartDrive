package com.app.smartdrive.api.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
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
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setEntityModifiedDate(LocalDateTime.now());
    Long businessEntityId = businessEntityService.save(businessEntity);

    UserRolesId userRolesId = new UserRolesId(businessEntityId, roleName.CU);
    
    UserRoles userRoles = new UserRoles();
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    List<UserRoles> listRole = List.of(userRoles); 
    
    UserPhoneId userPhoneId = new UserPhoneId(businessEntityId, userPost.getUserPhoneNumber());


    UserPhone userPhone = new UserPhone();
    userPhone.setUserPhoneId(userPhoneId);
    userPhone.setUsphPhoneType("HP");
        
    List<UserPhone> listPhone = List.of(userPhone);
    
    User user = new User();
    user.setUserBusinessEntity(businessEntity);
    user.setUserEntityId(businessEntityId);
    user.setUserName(userPost.getUserName());
    user.setUserPassword(userPost.getUserPassword());
    user.setUserFullName(userPost.getFullName());
    user.setUserEmail(userPost.getEmail());
    user.setUserBirthPlace(userPost.getBirthPlace());
    user.setUserBirthDate(userPost.getUserBirthDate());
    user.setUserNPWP(userPost.getUserNpwp());
    user.setUserNationalId(userPost.getUserNationalId()+businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());
    user.setUserPhone(listPhone);
    user.setUserRoles(listRole);
    userPhone.setUser(user);
    userRoles.setUser(user);
    User userSaver = userServiceImpl.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userSaver);
  }
  
}
