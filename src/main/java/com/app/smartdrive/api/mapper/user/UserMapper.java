package com.app.smartdrive.api.mapper.user;

import java.util.ArrayList;
import java.util.List;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRoles;

public class UserMapper {
  public static UserDto convertUserToDto(User user){
    List<String> listRole = new ArrayList<>();
    for (UserRoles role : user.getUserRoles()) {
      listRole.add(role.getRoles().getRoleName().getValue());
    }
    UserDto userDto = UserDto.builder()
      .userPhone(user.getUserPhone())
      .userEmail(user.getUserEmail())
      .userAddress(user.getUserAddress())
      .userAccounts(user.getUserAccounts())
      .userPhoto(user.getUserPhoto())
      .userRoles(user.getUserRoles())
      .build();
      return userDto;
  }
}
