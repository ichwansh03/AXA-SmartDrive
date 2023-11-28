package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPhoneImpl implements UserPhoneService {
  private final UserRepository userRepository;
  private final UserPhoneRepository userPhoneRepository;
  private final UserService userService;
  private final EntityManager entityManager;
  private final Logger logger = LoggerFactory.getLogger(UserPhoneImpl.class);

  @Override
  @Transactional
  public UserPhone updateUserPhone(Long userId, String phoneNumber, CreateUserDto userPost) {
    // TODO Auto-generated method stub
    // User user = userRepository.findById(userId).get();
    // List<UserPhone> listUserPhones = user.getUserPhone();
    // UserPhone userPhone = userPhoneRepository.findByUsphPhoneNumber(phoneNumber).get();
    // int indexPhone = listUserPhones.indexOf(userPhone);
    // if(userPhone!=null){
    // entityManager.persist(userPhone);
    // UserPhoneId userPhoneId = userPhone.getUserPhoneId();
    // NullUtils.updateIfChanged(userPhoneId::setUsphPhoneNumber, userPost.getUserPhoneNumber(),
    // userPhoneId::getUsphPhoneNumber);
    // userPhoneId.setUsphEntityId(userId);
    // userPhone.setUserPhoneId(userPhoneId);
    // userPhone.setUser(user);
    // entityManager.flush();
    // listUserPhones.set(indexPhone, userPhone);
    // user.setUserPhone(listUserPhones);

    // User user = userRepository.findById(userId).get();
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumber(phoneNumber);
    if(userPhone.isPresent() && userPhone.get().getUserPhoneId().getUsphEntityId().equals(userId)){
      userPhoneRepository.setPhoneNumber(userPost.getUserPhoneNumber(), phoneNumber);
      return userPhoneRepository.findByUsphPhoneNumber(userPost.getUserPhoneNumber()).get();
    }
    throw new EntityNotFoundException("Phone Number is not exist or you are not granted access to this phone number");
  }

  @Override
  public UserPhone addUserPhone(Long id, CreateUserDto userPost) {
    User user = userRepository.findById(id).get();
    UserPhoneId userPhoneId = new UserPhoneId(user.getUserEntityId(), userPost.getUserPhoneNumber());
    UserPhone userPhone = new UserPhone();
    userPhone.setUserPhoneId(userPhoneId);
    userPhone.setUsphPhoneType(userPost.getPhoneType());
    userPhone.setUsphModifiedDate(LocalDateTime.now());
    userPhone.setUser(user);
    return userPhoneRepository.save(userPhone);
  }

  @Override
  public void deleteUserPhone(Long id, String number) {
    Optional<User> user = userRepository.findById(id);
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumber(number);
    if(user.get().getUserEntityId().equals(userPhone.get().getUserPhoneId().getUsphEntityId())){
      userPhoneRepository.delete(userPhone.get());
    }
  }


}
