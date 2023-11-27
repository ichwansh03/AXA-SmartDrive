package com.app.smartdrive.api.services.users.implementation;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserAddressService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressImpl implements UserAddressService {

  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;
  private final CityRepository cityRepository;

  @Override
  public UserAddress updateUserAddress(Long id, Long idAddress, CreateUserDto userPost) {
    // TODO Auto-generated method stub
    Optional<UserAddress> userAddress = userAddressRepository.findByUserAdressIdUsdrId(idAddress);
    if (userAddress.isPresent()
        && userAddress.get().getUserAdressId().getUsdrEntityId().equals(id)) {
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAddress1, userPost.getAddress1(),
          userAddress.get()::getUsdrAddress1);
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAdress2, userPost.getAddress2(),
          userAddress.get()::getUsdrAdress2);
      Cities city = cityRepository.findByCityName(userPost.getCity());
      userAddress.get().setCity(city);
      return userAddressRepository.save(userAddress.get());
    }
    throw new EntityNotFoundException("Address not found or you're not granted access");
  }

}
