package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
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
  public UserAddress updateUserAddress(User user, UserAddress address, @Valid UserAddressRequestDto userPost) {
    // Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(idAddress);
    // User user = userRepository.findById(id).get();

    // if (userAddress.isPresent()
    //     && userAddress.get().getUsdrEntityId().equals(id)) {
      
    // }
    TransactionMapper.mapDtoToEntity(userPost, address);
    Cities city = cityRepository.findById(userPost.getCityId()).orElseThrow(() -> new EntityNotFoundException("City not found"));
    address.setCity(city);
    address.setUsdrModifiedDate(LocalDateTime.now());
    address.setUser(user);
      // return userAddressRepository.save(userAddress.get());
    return address;
  }

  @Override
  @Transactional
  public UserAddress updateUserAddress(Long id, Long addressId, @Valid UserAddressRequestDto userPost) {
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(addressId, id);
    User user = userRepository.findById(id).get();
    if (userAddress.isPresent()) {
      updateUserAddress(user, userAddress.get(), userPost);
      return save(userAddress.get());
    }
    throw new EntityNotFoundException("Address not found in this user");
  }

  @Override
  public UserAddress addUserAddress(Long id, @Valid UserAddressRequestDto userPost) {
    UserAddress userAddress = new UserAddress();
    userAddress.setUsdrEntityId(id);
    User user = userRepository.findById(id).get();
    TransactionMapper.mapDtoToEntity(userPost, userAddress);

    Cities cities = cityRepository.findById(userPost.getCityId()).orElseThrow(() -> new EntityNotFoundException("City not found"));
    userAddress.setCity(cities);
    userAddress.setUser(user);
    userAddress.setUsdrCityId(userPost.getCityId());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());
    return userAddressRepository.save(userAddress);
  }

  @Override
  @Transactional
  public void deleteAddressById(Long id, Long addressId){
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(addressId, id);
    if(userAddress.isPresent()){
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

  @Override
  public UserAddress save(UserAddress userAddress) {
    return userAddressRepository.save(userAddress);
  }

  
}
