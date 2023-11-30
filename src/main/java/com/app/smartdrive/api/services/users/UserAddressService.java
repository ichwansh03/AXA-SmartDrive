package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.UserAddressDto;
import com.app.smartdrive.api.entities.users.UserAddress;

public interface UserAddressService {
  UserAddress updateUserAddress(Long id, Long idAddress, UserAddressDto userPost);
  UserAddress createUserAddress(Long id, UserAddressDto userPost);
  void deleteAddressById(Long id, Long addressId);
}
