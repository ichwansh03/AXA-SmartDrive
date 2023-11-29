package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.users.UserAddress;

public interface UserAddressService {
  UserAddress updateUserAddress(Long id, Long idAddress, CreateUserDto userPost);
  UserAddress createUserAddress(Long id, CreateUserDto userPost);
  void deleteAddressById(Long id, Long addressId);
}
