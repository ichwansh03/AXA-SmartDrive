package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.dto.request.UserPhoneRequestDto;
import com.smartdrive.userservice.dto.response.UserPhoneDto;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.entities.UserPhone;
import com.smartdrive.userservice.entities.UserPhoneId;
import com.smartdrive.userservice.mapper.TransactionMapper;
import com.smartdrive.userservice.repositories.UserPhoneRepository;
import com.smartdrive.userservice.repositories.UserRepository;
import com.smartdrive.userservice.services.UserPhoneService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPhoneImpl implements UserPhoneService {
  private final UserRepository userRepository;
  private final UserPhoneRepository userPhoneRepository;

  @Override
  @Transactional
  public UserPhone updateUserPhone(Long userId, String phoneNumber, UserPhoneRequestDto userPost) {
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
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumberAndUserId(phoneNumber, userId);
    if(userPhone.isPresent()){
      userPhone.get().setUsphModifiedDate(LocalDateTime.now());
      userPhone.get().setUsphPhoneType(userPost.getUsphPhoneType());
      userPhoneRepository.save(userPhone.get());
      userPhoneRepository.setPhoneNumber(userPost.getUsphPhoneNumber(), phoneNumber);
      return userPhoneRepository.findByUsphPhoneNumberAndUserId(userPost.getUsphPhoneNumber(), userId).get();
    }
    throw new EntityNotFoundException("Phone Number is not exist or you are not granted access to this phone number");
  }

  @Override
  public UserPhone addUserPhone(Long id, UserPhoneDto userPost) {

    User user = userRepository.findById(id).get();
    UserPhoneId userPhoneId = new UserPhoneId(user.getUserEntityId(), userPost.getUserPhoneId().getUsphPhoneNumber());
    UserPhone userPhone = new UserPhone();
    userPhone.setUserPhoneId(userPhoneId);
    userPhone.setUsphPhoneType(userPost.getUsphPhoneType());
    userPhone.setUsphModifiedDate(LocalDateTime.now());
    userPhone.setUser(user);
    return userPhoneRepository.save(userPhone);
  }

  @Override
  public void deleteUserPhone(Long id, String number) {
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumberAndUserId(number, id);
    if(userPhone.isPresent()){
      userPhoneRepository.delete(userPhone.get());
    }
  }

  @Override
  public List<UserPhone> createUserPhone(User user, List<UserPhoneDto> userPost) {
    List<UserPhone> userPhone = TransactionMapper.mapListDtoToListEntity(userPost, UserPhone.class);
    userPhone.forEach(phone -> {
      phone.setUser(user);
      phone.getUserPhoneId().setUsphEntityId(user.getUserEntityId());
      phone.setUsphModifiedDate(LocalDateTime.now());
    });
    user.setUserPhone(userPhone);
    return userPhone;
  }
}
