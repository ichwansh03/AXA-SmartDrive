package com.app.smartdrive.api.services.users.implementation;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserAddressService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressImpl implements UserAddressService {

  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;
  private final CityRepository cityRepository;

  @Override
  @Transactional
  public UserAddress updateUserAddress(Long id, Long idAddress, CreateUserDto userPost) {
    // TODO Auto-generated method stub
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(idAddress);
    if (userAddress.isPresent()
        && userAddress.get().getUserAdressId().getUsdrEntityId().equals(id)) {
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAddress1, userPost.getAddress1(),
          userAddress.get()::getUsdrAddress1);
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAdress2, userPost.getAddress2(),
          userAddress.get()::getUsdrAdress2);
      Cities city = cityRepository.findByCityName(userPost.getCity());
      userAddress.get().setCity(city);
      userAddress.get().setUsdrModifiedDate(LocalDateTime.now());
      return userAddressRepository.save(userAddress.get());
    }
    throw new EntityNotFoundException("Address not found or you're not granted access");
  }

  @Override
  public UserAddress createUserAddress(Long id, CreateUserDto userPost) {
    UserAddress userAddress = new UserAddress();
    Optional<UserAddress> findTopByOrderByIdDesc = userAddressRepository.findLastOptional();
    Long lastIndexUsdr;
    if (findTopByOrderByIdDesc.isPresent()) {
      lastIndexUsdr = findTopByOrderByIdDesc.get().getUserAdressId().getUsdrId();
    } else {
      lastIndexUsdr = 1L;
    }
    UserAdressId userAdressId = new UserAdressId(lastIndexUsdr+1, id);
    userAddress.setUserAdressId(userAdressId);
    User user = userRepository.findById(id).get();
    userAddress.setUsdrAddress1(userPost.getAddress1());
    userAddress.setUsdrAdress2(userPost.getAddress2());
    Cities cities = cityRepository.findByCityName(userPost.getCity());
    userAddress.setCity(cities);
    userAddress.setUser(user);
    userAddress.setUsdrCityId(cities.getCityId());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());

    return userAddressRepository.save(userAddress);
  }

  @Override
  @Transactional
  public void deleteAddressById(Long id, Long addressId){
    Optional<User> user = userRepository.findById(id);
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(addressId);
    if(user.get().getUserEntityId().equals(userAddress.get().getUserAdressId().getUsdrEntityId())){
      userAddressRepository.delete(userAddress.get());;
    }
  }
}
