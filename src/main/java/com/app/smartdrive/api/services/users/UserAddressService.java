package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;

public interface UserAddressService {
  UserAddress updateUserAddress(Long id, Long idAddress, UserAddressDto userPost);

  UserAddress addUserAddress(Long id, UserAddressDto userPost);

  List<UserAddress> createUserAddress(User user, List<UserAddressDto> userPost, Long cityId);

  void deleteAddressById(Long id, Long addressId);
}
