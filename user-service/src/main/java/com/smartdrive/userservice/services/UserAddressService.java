package com.smartdrive.userservice.services;


import com.smartdrive.userservice.dto.request.UserAddressRequestDto;
import com.smartdrive.userservice.dto.response.UserAddressDto;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.entities.UserAddress;

import java.util.List;

public interface UserAddressService {
  UserAddress updateUserAddress(User user, UserAddress address, UserAddressRequestDto userPost);

  UserAddress updateUserAddress(Long id, Long addressId, UserAddressRequestDto userPost);

  UserAddress addUserAddress(Long id, UserAddressRequestDto userPost);

  List<UserAddress> createUserAddress(User user, List<UserAddressDto> userPost, Long cityId);

  void deleteAddressById(Long id, Long addressId);

  UserAddress save(UserAddress userAddress);
}
