package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;

public interface UserPhoneService {
  UserPhone updateUserPhone(Long id, String phoneNumber, CreateUserDto userPost);
  UserPhone addUserPhone(Long id, CreateUserDto userPost);
  void deleteUserPhone(Long id, String number);
}