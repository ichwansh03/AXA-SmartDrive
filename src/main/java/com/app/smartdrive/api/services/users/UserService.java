package com.app.smartdrive.api.services.users;

import java.util.Optional;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.services.master.MasterService;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends MasterService<User, Long> {
  User createAdmin(CreateUserDto userPost);

  User save(User user);

  User createUserCustomer(CreateUserDto userPost, RoleName roleName);

  User createUserCustomerByAgen(CreateUserDto userPost, Boolean grantAccessUser);

  User createUser(ProfileRequestDto userPost);

  UpdateUserRequestDto updateUser(UpdateUserRequestDto userPost, Long id);

  Optional<User> getUserById(Long id);

  UserDetailsService userDetailsService();

  void changeEmail(Long id, String newEmail);



}
