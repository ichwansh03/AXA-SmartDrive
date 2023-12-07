package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final UserRolesService userRolesService;
  private final UserPhoneService userPhoneService;
  private final UserAddressService userAddressService;
  private final UserUserAccountService userAccountService;
//  private final PasswordEncoder passwordEncoder;

  @Override
  public User createUser(ProfileRequestDto userPost) {
    BusinessEntity businessEntity = businessEntityService.createBusinessEntity();
    User newUser = new User();
    User user = TransactionMapper.mapDtoToEntity(userPost, newUser);
    businessEntity.setUser(user);
    user.setUserBusinessEntity(businessEntity);
//    if(userPost.getUserPassword() != null){
//      String hashedPw = passwordEncoder.encode(userPost.getUserPassword());
//      user.setUserPassword(hashedPw);
//    }
    user.setUserEntityId(businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());
    return user;
  }

  @Override
  public UserDto getByIdDto(Long id) {
    return TransactionMapper.mapEntityToDto(userRepo.findById(id).get(), UserDto.class);
  }

  @Override
  public List<UserDto> getAllDto() {
    List<User> users = userRepo.findAll();
    List<UserDto> userDto = TransactionMapper.mapEntityListToDtoList(users, UserDto.class);
    return userDto;
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
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

    return save(user);
  }

  @Transactional
  @Override
  public User save(User user) {
    User newUser = userRepo.save(user);
//    userRepo.flush();
    return newUser;
  }

  @Override
  @Transactional
  public UpdateUserRequestDto save(UpdateUserRequestDto userPost, Long id){
    User user = userRepo.findById(id).orElseThrow(
      () -> new EntityNotFoundException("User not found")
      );
    TransactionMapper.mapDtoToEntity(userPost, user);
    // String path = "src/test/resources/image/";
    // Path filePath = Paths.get(path+user.getUserPhoto());
    // Files.delete(filePath);
    save(user);
    return TransactionMapper.mapEntityToDto(user, UpdateUserRequestDto.class);
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepo.findById(id);
  }

  @Override
  public String loginUser(String identity, String password, List<RoleName> roleName) {
    Optional<User> user = userRepo.findUserByIden(identity);
    if (user.isPresent()) {
      List<RoleName> listRole = user.get().getUserRoles().stream().map(role -> role.getRoles().getRoleName()).collect(Collectors.toList());
      if (listRole.stream().anyMatch(roleName::contains)) {
        if (true) {
          return "Access Granted";
        }
        return "Wrong Password";
      }
      return "Access Denied";
    }
    return "Input email atau username atau phoneNumber salah";
  }

  @Override
  public String loginCustomer(String identity, String password) {
    return loginUser(identity, password, Arrays.asList(RoleName.PC, RoleName.CU));
  }

  @Override
  public String loginEmployee(String identity, String password) {
    return loginUser(identity, password, Collections.singletonList(RoleName.EM));
  }

  @Override
  @Transactional
  public String changePassword(Long id,PasswordRequestDto passwordRequestDto) {
    User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    if(true){
      if(passwordRequestDto.getNewPassword().equals(passwordRequestDto.getConfirmPassword())){
        user.setUserPassword("true");
        save(user);
        return "password has been changed";
      }
      return "Confirm password must be the same as the new password";
    }
    return "Current password is wrong";
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getById'");
  }

  @Override
  public List<User> getAll() {
    List<User> users = userRepo.findAll();
    return users;
  }



}
