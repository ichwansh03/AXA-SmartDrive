package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumber(phoneNumber, userId);
    if(userPhone.isPresent()){
      userPhone.get().setUsphModifiedDate(LocalDateTime.now());
      userPhone.get().setUsphPhoneType(userPost.getUsphPhoneType());
      userPhoneRepository.save(userPhone.get());
      userPhoneRepository.setPhoneNumber(userPost.getUsphPhoneNumber(), phoneNumber);
      return userPhoneRepository.findByUsphPhoneNumber(userPost.getUsphPhoneNumber(), userId).get();
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
    Optional<UserPhone> userPhone = userPhoneRepository.findByUsphPhoneNumber(number, id);
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
