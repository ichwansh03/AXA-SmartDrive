package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserPhoneService;
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
    userPhone.ifPresent(userPhoneRepository::delete);
  }

  @Override
  public Optional<String> findByPhoneNumber(String phoneNumber) {
    return this.userPhoneRepository.findPhoneNumber(phoneNumber);
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
