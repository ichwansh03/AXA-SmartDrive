package com.app.smartdrive.api.mapper.user;

import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;

public class UserMapper {
  public static UserDto convertUserToDto(User user){
    UserDto userDto = UserDto.builder()
      .userPhone(user.getUserPhone())
      .userEmail(user.getUserEmail())
      .userAddresses(user.getUserAddress())
      .user_accounts(user.getUserAccounts())
      .build();
      return userDto;
  }
}
