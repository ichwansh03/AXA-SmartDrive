package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.mapper.TransactionMapper;
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
  public UserAddress updateUserAddress(Long id, Long idAddress, UserAddressDto userPost) {
    // TODO Auto-generated method stub
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(idAddress);
    if (userAddress.isPresent()
        && userAddress.get().getUsdrEntityId().equals(id)) {
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAddress1, userPost.getUsdrAddress1(),
          userAddress.get()::getUsdrAddress1);
      NullUtils.updateIfChanged(userAddress.get()::setUsdrAddress2, userPost.getUsdrAddress2(),
          userAddress.get()::getUsdrAddress2);
      // Cities city = cityRepository.findByCityName(userPost.getCity());
      // userAddress.get().setCity(city);
      userAddress.get().setUsdrModifiedDate(LocalDateTime.now());
      return userAddressRepository.save(userAddress.get());
    }
    throw new EntityNotFoundException("Address not found or you're not granted access");
  }

  @Override
  public UserAddress addUserAddress(Long id, UserAddressDto userPost) {
    UserAddress userAddress = new UserAddress();
    // Optional<UserAddress> findTopByOrderByIdDesc = userAddressRepository.findLastOptional();
    // Long lastIndexUsdr;
    // if (findTopByOrderByIdDesc.isPresent()) {
    //   lastIndexUsdr = findTopByOrderByIdDesc.get().getUsdrId();
    // } else {
    //   lastIndexUsdr = 1L;
    // }
    // UserAdressId userAdressId = new UserAdressId(lastIndexUsdr+1, id);
    // userAddress.setUserAdressId(userAdressId);
    userAddress.setUsdrEntityId(id);
    User user = userRepository.findById(id).get();
    userAddress.setUsdrAddress1(userPost.getUsdrAddress1());
    userAddress.setUsdrAddress2(userPost.getUsdrAddress2());
    // Cities cities = cityRepository.findByCityName(userPost.getCity());
    // userAddress.setCity(cities);
    userAddress.setUser(user);
    // userAddress.setUsdrCityId(cities.getCityId());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());

    return userAddressRepository.save(userAddress);
  }

  @Override
  @Transactional
  public void deleteAddressById(Long id, Long addressId){
    Optional<User> user = userRepository.findById(id);
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(addressId);
    if(user.get().getUserEntityId().equals(userAddress.get().getUsdrEntityId())){
      userAddressRepository.delete(userAddress.get());
    }
  }

  @Override
  public List<UserAddress> createUserAddress(User user, List<UserAddressDto> userPost, Long cityId) {
    List<UserAddress> userAddress = TransactionMapper.mapListDtoToListEntity(userPost, UserAddress.class);
    userAddress.forEach(address -> {
      Cities city = cityRepository.findById(cityId).orElse(null);
      address.setCity(city);
      address.setUsdrEntityId(user.getUserEntityId());
      address.setUsdrCityId(cityId);
      address.setUsdrModifiedDate(LocalDateTime.now());
      address.setUser(user);
    });
    user.setUserAddress(userAddress);
    return userAddress;
  }
}
