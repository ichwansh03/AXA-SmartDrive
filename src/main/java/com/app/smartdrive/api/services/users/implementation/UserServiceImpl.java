package com.app.smartdrive.api.services.users.implementation;

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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    User newUser = new User();
    User user = TransactionMapper.mapDtoToEntity(userPost, newUser);
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
  public User createUserCustomer(CreateUserDto userPost) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(RoleName.CU, user);

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

    userRolesService.createUserRole(RoleName.AD, user);

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
    User newUser = userRepo.save(user);
    return newUser;
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
  public Optional<User> getUserById(Long id) {
    return userRepo.findById(id);
  }

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByIden(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
      }
    };
  }

  @Override
  public User getById(Long id) {
    return userRepo.findById(id).orElseThrow(
            () -> new com.app.smartdrive.api.Exceptions.EntityNotFoundException("User not found!"));
  }

  @Override
  public List<User> getAll() {
    List<User> users = userRepo.findAll();
    return users;
  }
}
