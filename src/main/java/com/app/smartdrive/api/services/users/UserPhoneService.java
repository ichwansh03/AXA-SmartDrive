package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.UserPhoneDto;
import com.app.smartdrive.api.entities.users.UserPhone;

public interface UserPhoneService {
  UserPhone updateUserPhone(Long id, String phoneNumber, UserPhoneDto userPost);
  UserPhone addUserPhone(Long id, UserPhoneDto userPost);
  void deleteUserPhone(Long id, String number);
}