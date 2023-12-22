package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;

import javax.swing.text.html.Option;

public interface UserPhoneService {
  UserPhone updateUserPhone(Long id, String phoneNumber, UserPhoneRequestDto userPost);

  UserPhone addUserPhone(Long id, UserPhoneDto userPost);

  List<UserPhone> createUserPhone(User user, List<UserPhoneDto> userPost);

  void deleteUserPhone(Long id, String number);

  Optional<String> findByPhoneNumber(String phoneNumber);
}
