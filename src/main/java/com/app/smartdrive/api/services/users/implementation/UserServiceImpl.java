package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.payment.User_accounts;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.mapper.user.UserMapper;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final EntityManager entityManager;
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final RolesRepository rolesRepository;
  private final CityRepository cityRepository;

  @Override
  public UserDto getById(Long id) {
    // TODO Auto-generated method stub
    return UserMapper.convertUserToDto(userRepo.findById(id).get());
  }

  @Override
  public List<UserDto> getAll() {
    // TODO Auto-generated method stub
    List<User> users = userRepo.findAll();
    List<UserDto> userDto = new ArrayList<>();
    for (User user : users) {
      userDto.add(UserMapper.convertUserToDto(user));
    }
    return userDto;
  }

  @Override
  public UserDto save(UserDto entity) {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public void deleteById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

  @Override
  @Transactional
  public User create(CreateUserDto userPost) {
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setEntityModifiedDate(LocalDateTime.now());
    Long businessEntityId = businessEntityService.save(businessEntity);

    UserRolesId userRolesId = new UserRolesId(businessEntityId, roleName.CU);

    Roles roles = rolesRepository.findById(roleName.CU).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    List<UserRoles> listRole = List.of(userRoles);

    UserPhoneId userPhoneId = new UserPhoneId(businessEntityId, userPost.getUserPhoneNumber());


    UserPhone userPhone = new UserPhone();
    userPhone.setUserPhoneId(userPhoneId);
    userPhone.setUsphPhoneType("HP");

    List<UserPhone> listPhone = List.of(userPhone);

    Cities city = cityRepository.findByCityName(userPost.getCity());

    UserAdressId userAdressId = new UserAdressId();
    userAdressId.setUsdrEntityId(businessEntityId);
    UserAddress userAddress = new UserAddress();
    userAddress.setUsdrCityId(city.getCityId());
    userAddress.setUserAdressId(userAdressId);
    userAddress.setCity(city);
    userAddress.setUsdrAddress1(userPost.getAddress1());
    userAddress.setUsdrAdress2(userPost.getAddress2());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());

    List<UserAddress> listAddress = List.of(userAddress);


    User_accounts user_accounts = new User_accounts();


    User user = new User();
    user.setUserBusinessEntity(businessEntity);
    user.setUserEntityId(businessEntityId);
    user.setUserName(userPost.getUserName());
    user.setUserPassword(userPost.getUserPassword());
    user.setUserFullName(userPost.getFullName());
    user.setUserEmail(userPost.getEmail());
    user.setUserBirthPlace(userPost.getBirthPlace());
    user.setUserBirthDate(userPost.getUserBirthDate());
    user.setUserNPWP(userPost.getUserNpwp());
    user.setUserNationalId(userPost.getUserNationalId() + businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());
    user.setUserPhone(listPhone);
    user.setUserRoles(listRole);
    user.setUserAddress(listAddress);

    userPhone.setUser(user);
    userRoles.setUser(user);
    userAddress.setUser(user);
    User userSaver = save(user);
    return userSaver;
  }

  @Transactional
  @Override
  public User save(User user) {
    entityManager.persist(user);
    entityManager.flush();
    return user;
  }

  @Override
  @Transactional
  public User save(CreateUserDto userPost, Long id) {
    User user = userRepo.findById(id).get();
    NullUtils.updateIfChanged(user::setUserName, userPost.getUserName(), user::getUserName);
    NullUtils.updateIfChanged(user::setUserFullName, userPost.getFullName(), user::getUserFullName);
    NullUtils.updateIfChanged(user::setUserEmail, userPost.getEmail(), user::getUserEmail);
    NullUtils.updateIfChanged(user::setUserBirthPlace, userPost.getBirthPlace(),
        user::getUserBirthPlace);
    NullUtils.updateIfChanged(user::setUserBirthDate, userPost.getUserBirthDate(),
        user::getUserBirthDate);
    User userSaved = save(user);
    return userSaved;
  }

}
