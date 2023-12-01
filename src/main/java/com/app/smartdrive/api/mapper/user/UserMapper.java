package com.app.smartdrive.api.mapper.user;

import java.util.ArrayList;
import java.util.List;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserRoleDto;
import com.app.smartdrive.api.dto.user.response.UserUserAccountDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.mapper.TransactionMapper;

public class UserMapper {
  public static UserDto convertUserToDto(User user){
    List<String> listRole = new ArrayList<>();
    for (UserRoles role : user.getUserRoles()) {
      listRole.add(role.getRoles().getRoleName().getValue());
    }


    UserDto userDto = UserDto.builder()
      .userPhone(TransactionMapper.mapEntityListToDtoList(user.getUserPhone(), UserPhoneDto.class))
      .userEmail(user.getUserEmail())
      .userAddress(TransactionMapper.mapEntityListToDtoList(user.getUserAddress(), UserAddressDto.class))
      .userAccounts(TransactionMapper.mapEntityListToDtoList(user.getUserAccounts(), UserUserAccountDto.class))
      .userPhoto(user.getUserPhoto())
      .userRoles(TransactionMapper.mapEntityListToDtoList(user.getUserRoles(), UserRoleDto.class))
      .userFullName(user.getUserFullName())
      .build();
      return userDto;
  }
}
