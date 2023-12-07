package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;

public interface UserAddressService {
  UserAddress updateUserAddress(User user, UserAddress address, UserAddressRequestDto userPost);

  UserAddress updateUserAddress(Long id, Long addressId, UserAddressRequestDto userPost);

  UserAddress addUserAddress(Long id, UserAddressRequestDto userPost);

  List<UserAddress> createUserAddress(User user, List<UserAddressDto> userPost, Long cityId);

  void deleteAddressById(Long id, Long addressId);

  UserAddress save(UserAddress userAddress);
}
