package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;

public interface UserPhoneService {
  UserPhone updateUserPhone(Long id, String phoneNumber, UserPhoneDto userPost);

  UserPhone addUserPhone(Long id, UserPhoneDto userPost);

  List<UserPhone> createUserPhone(User user, List<UserPhoneDto> userPost);

  void deleteUserPhone(Long id, String number);
}
