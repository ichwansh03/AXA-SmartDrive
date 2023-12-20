package com.smartdrive.userservice.services;


import com.smartdrive.userservice.dto.request.UserPhoneRequestDto;
import com.smartdrive.userservice.dto.response.UserPhoneDto;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.entities.UserPhone;

import java.util.List;

public interface UserPhoneService {
  UserPhone updateUserPhone(Long id, String phoneNumber, UserPhoneRequestDto userPost);

  UserPhone addUserPhone(Long id, UserPhoneDto userPost);

  List<UserPhone> createUserPhone(User user, List<UserPhoneDto> userPost);

  void deleteUserPhone(Long id, String number);
}
