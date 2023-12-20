package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.dto.request.CreateUserDto;
import com.smartdrive.userservice.dto.request.ProfileRequestDto;
import com.smartdrive.userservice.dto.request.UpdateUserRequestDto;
import com.smartdrive.userservice.entities.BusinessEntity;
import com.smartdrive.userservice.entities.EnumUsers;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.mapper.TransactionMapper;
import com.smartdrive.userservice.repositories.UserRepository;
import com.smartdrive.userservice.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.smartdrive.userservice.exceptions.EntityNotFoundException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final UserRolesService userRolesService;
  private final UserPhoneService userPhoneService;
  private final UserAddressService userAddressService;
//  private final UserUserAccountService userAccountService;
//  private final RefreshTokenService refreshTokenService;

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

//  @Override
//  @Transactional
//  public void deleteById(Long id) {
//    refreshTokenService.deleteByUserId(id);
//    userRepo.deleteById(id);
//  }

  @Override
  @Transactional
  public User createUserCustomer(CreateUserDto userPost) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(EnumUsers.RoleName.PC, user);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
        : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

//    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

    return user;
  }

  @Override
  @Transactional
  public User createUserCustomerByAgen(CreateUserDto userPost, Boolean grantAccessUser) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRoleByAgen(EnumUsers.RoleName.CU, user, grantAccessUser);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
            : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

//    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

    return user;
  }

  @Override
  @Transactional
  public User createAdmin(CreateUserDto userPost){
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(EnumUsers.RoleName.AD, user);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
            : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

//    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);

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

//  @Override
//  public UserDetailsService userDetailsService() {
//    return new UserDetailsService() {
//      @Override
//      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepo.findUserByIden(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
//      }
//    };
//  }

  @Override
  public User getById(Long id) {
    return userRepo.findById(id).orElseThrow(
            () -> new EntityNotFoundException("User not found!"));
  }

  @Override
  public List<User> getAll() {
    List<User> users = userRepo.findAll();
    return users;
  }
}
