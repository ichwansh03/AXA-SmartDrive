package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
  User createAdmin(CreateUserDto userPost);

  User save(User user);

  @Transactional
  void deleteById(Long id);

  User createUserCustomer(CreateUserDto userPost, RoleName roleName);

  User createUserCustomerByAgen(CreateUserDto userPost, Boolean grantAccessUser);

  User createUser(ProfileRequestDto userPost);

  UpdateUserRequestDto updateUser(UpdateUserRequestDto userPost, Long id);

  User getById(Long id);

  List<User> getAll();

  void changeEmail(Long id, String newEmail);

  void validateNPWP(String npwp);

  void validateNationalId(String nationalId);
}
