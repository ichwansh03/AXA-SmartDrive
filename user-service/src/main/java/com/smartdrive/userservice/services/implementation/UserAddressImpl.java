package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.clients.MasterClient;
import com.smartdrive.userservice.dto.request.UserAddressRequestDto;
import com.smartdrive.userservice.dto.response.CitiesRes;
import com.smartdrive.userservice.dto.response.UserAddressDto;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.entities.UserAddress;
import com.smartdrive.userservice.mapper.TransactionMapper;
import com.smartdrive.userservice.repositories.UserAddressRepository;
import com.smartdrive.userservice.repositories.UserRepository;
import com.smartdrive.userservice.services.UserAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAddressImpl implements UserAddressService {

  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;
  private final MasterClient masterClient;

  @Override
  public UserAddress updateUserAddress(User user, UserAddress address, UserAddressRequestDto userPost) {
    // Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(idAddress);
    // User user = userRepository.findById(id).get();

    // if (userAddress.isPresent()
    //     && userAddress.get().getUsdrEntityId().equals(id)) {
      
    // }
    TransactionMapper.mapDtoToEntity(userPost, address);
//    Cities city = cityRepository.findById(userPost.getCityId())
//            .orElseThrow(() -> new EntityNotFoundException("City not found"));
    CitiesRes city = masterClient.findDataById(userPost.getCityId());
    address.setUsdrCityId(city.getCityId());
    address.setUsdrModifiedDate(LocalDateTime.now());
    address.setUser(user);
      // return userAddressRepository.save(userAddress.get());
    return address;
  }

  @Override
  @Transactional
  public UserAddress updateUserAddress(Long id, Long addressId, UserAddressRequestDto userPost) {
    Optional<UserAddress> userAddress = userAddressRepository.findUserAddressOptional(addressId, id);
    User user = userRepository.findById(id).get();
    if (userAddress.isPresent()) {
      updateUserAddress(user, userAddress.get(), userPost);
      return save(userAddress.get());
    }
    throw new EntityNotFoundException("Address not found in this user");
  }

  @Override
  public UserAddress addUserAddress(Long id, UserAddressRequestDto userPost) {
    UserAddress userAddress = new UserAddress();
    userAddress.setUsdrEntityId(id);
    User user = userRepository.findById(id).get();
    TransactionMapper.mapDtoToEntity(userPost, userAddress);

    CitiesRes cities = masterClient.findDataById(userPost.getCityId());
//    userAddress.setCity(cities);
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
      CitiesRes city = masterClient.findDataById(cityId);
//      address.setCity(city);
      address.setUsdrEntityId(user.getUserEntityId());
      address.setUsdrCityId(city.getCityId());
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
