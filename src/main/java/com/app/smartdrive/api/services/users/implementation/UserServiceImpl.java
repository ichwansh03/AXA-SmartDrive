package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.Exceptions.UserExistException;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.refreshToken.RefreshTokenService;
import com.app.smartdrive.api.services.users.*;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final UserRolesService userRolesService;
  private final UserPhoneService userPhoneService;
  private final UserAddressService userAddressService;
  private final UserUserAccountService userAccountService;
  private final RefreshTokenService refreshTokenService;

  @Override
  public User createUser(ProfileRequestDto userPost) {
    BusinessEntity businessEntity = businessEntityService.createBusinessEntity();
    User user = new User();
    TransactionMapper.mapDtoToEntity(userPost, user);
    businessEntity.setUser(user);
    user.setUserBusinessEntity(businessEntity);
    user.setUserEntityId(businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());
    return user;
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    refreshTokenService.deleteByUserId(id);
    userRepo.deleteById(id);
  }

  @Override
  @Transactional
  public User createUserCustomer(CreateUserDto userPost, RoleName roleName) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(roleName, user, true);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
        : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

    return user;
  }

  @Override
  @Transactional
  public User createUserCustomerByAgen(CreateUserDto userPost, Boolean grantAccessUser) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(RoleName.CU, user, grantAccessUser);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
            : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

    return user;
  }

  @Override
  @Transactional
  public User createAdmin(CreateUserDto userPost){
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(RoleName.AD, user, true);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
            : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

    return user;
  }

  @Transactional
  @Override
  public User save(User user) {
    return userRepo.save(user);
  }

  @Override
  @Transactional
  public UpdateUserRequestDto updateUser(UpdateUserRequestDto userPost, Long id){
    User user = userRepo.findById(id).orElseThrow(
      () -> new EntityNotFoundException("User not found"));
    TransactionMapper.mapDtoToEntity(userPost, user);
    save(user);
    return TransactionMapper.mapEntityToDto(user, UpdateUserRequestDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public User getById(Long id) {
    return userRepo.findById(id).orElseThrow(
            () -> new com.app.smartdrive.api.Exceptions.EntityNotFoundException("User not found!"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAll() {
    List<User> users = userRepo.findAll();
    return users;
  }

  @Transactional
  @Override
  public void changeEmail(Long userId, String newEmail) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    user.setUserEmail(newEmail);
    save(user);
  }

  @Transactional(readOnly = true)
  @Override
  public void validateNPWP(String npwp) {
    if(userRepo.existsByUserNPWP(npwp)){
      throw new UserExistException("User with NPWP number " + npwp + " is already exist");
    }
  }

  @Override
  public void validateNationalId(String nationalId) {
    if(userRepo.existsByUserNationalId(nationalId)){
      throw new UserExistException("User with national id " + nationalId + " is already exist");
    }

  }


}
