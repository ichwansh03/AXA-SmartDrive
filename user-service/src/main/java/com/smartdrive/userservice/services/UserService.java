package com.smartdrive.userservice.services;


import com.smartdrive.userservice.dto.request.CreateUserDto;
import com.smartdrive.userservice.dto.request.ProfileRequestDto;
import com.smartdrive.userservice.dto.request.UpdateUserRequestDto;
import com.smartdrive.userservice.entities.User;
import jakarta.transaction.Transactional;

import java.util.Optional;


public interface UserService extends BaseService<User, Long> {
  @Transactional
  User createAdmin(CreateUserDto userPost);

  public User save(User user);

  public User createUserCustomer(CreateUserDto userPost);

  public User createUserCustomerByAgen(CreateUserDto userPost, Boolean grantAccessUser);

  public User createUser(ProfileRequestDto userPost);

  @Transactional
  UpdateUserRequestDto updateUser(UpdateUserRequestDto userPost, Long id);

  public Optional<User> getUserById(Long id);

//  public UserDetailsService userDetailsService();

}
