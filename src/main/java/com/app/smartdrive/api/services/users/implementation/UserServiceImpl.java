package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
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
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.utils.NullUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserAddressRepository userAddressRepository;
  private final EntityManager entityManager;
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final RolesRepository rolesRepository;
  private final CityRepository cityRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;
  private final UserPhoneRepository userPhoneRepository;

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

    Optional<UserAddress> findTopByOrderByIdDesc = userAddressRepository.findLastOptional();
    Long lastIndexUsdr;
    if (findTopByOrderByIdDesc.isPresent()) {
      lastIndexUsdr = findTopByOrderByIdDesc.get().getUserAdressId().getUsdrId();
    } else {
      lastIndexUsdr = 1L;
    }

    UserAdressId userAdressId = new UserAdressId();
    userAdressId.setUsdrId(lastIndexUsdr + 1);
    userAdressId.setUsdrEntityId(businessEntityId);
    UserAddress userAddress = new UserAddress();
    userAddress.setUsdrCityId(city.getCityId());
    userAddress.setUserAdressId(userAdressId);
    userAddress.setCity(city);
    userAddress.setUsdrAddress1(userPost.getAddress1());
    userAddress.setUsdrAdress2(userPost.getAddress2());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());

    List<UserAddress> listAddress = List.of(userAddress);

<<<<<<< HEAD
    UserAccounts userAccounts = new UserAccounts();
    userAccounts.setUsac_accountno(userPost.getAccNumber());
    if (userPost.getAccountType().equals("BANK")) {
      userAccounts.setEnumPaymentType(EnumPaymentType.BANK);
      Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
          .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
      userAccounts.setBanks(bank);
      userAccounts.setUsacBankEntityid(bank.getBank_entityid());
    }
    if (userPost.getAccountType().equals("FINTECH")) {
      userAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
      Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
          .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
      userAccounts.setFintech(fintech);
    }
    List<UserAccounts> listUserAccountuserAccounts = List.of(userAccounts);
=======
    UserAccounts UserAccounts = new UserAccounts();
    UserAccounts.setUsac_accountno(userPost.getAccNumber());
    if (userPost.getAccountType().equals("BANK")) {
      UserAccounts.setEnumPaymentType(EnumPaymentType.BANK);
      Banks bank = banksRepository.findByBankNameOptional(userPost.getBank())
          .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
      UserAccounts.setBanks(bank);
    }
    if (userPost.getAccountType().equals("FINTECH")) {
      UserAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
      Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getFintech())
          .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
      UserAccounts.setFintech(fintech);
    }
    List<UserAccounts> listUserAccounts = List.of(UserAccounts);
>>>>>>> 4a94b6ae8ceb76b16e300b14fcb19538dea10322

    user.setUserPhone(listPhone);
    user.setUserRoles(listRole);
    user.setUserAddress(listAddress);
<<<<<<< HEAD
    user.setUserAccounts(listUserAccountuserAccounts);
    userAccounts.setUser(user);
=======
    user.setUser_accounts(listUserAccounts);

    UserAccounts.setUser(user);
>>>>>>> 4a94b6ae8ceb76b16e300b14fcb19538dea10322
    userPhone.setUser(user);
    userRoles.setUser(user);
    userAddress.setUser(user);
    return save(user);
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
    User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    NullUtils.updateIfChanged(user::setUserName, userPost.getUserName(), user::getUserName);
    NullUtils.updateIfChanged(user::setUserFullName, userPost.getFullName(), user::getUserFullName);
    NullUtils.updateIfChanged(user::setUserBirthPlace, userPost.getBirthPlace(),
        user::getUserBirthPlace);
    NullUtils.updateIfChanged(user::setUserBirthDate, userPost.getUserBirthDate(),
        user::getUserBirthDate);

    NullUtils.updateIfChanged(user::setUserPassword, userPost.getUserPassword(), user::getUserPassword);

    NullUtils.updateIfChanged(user::setUserEmail, userPost.getEmail(), user::getUserEmail); 

    Optional<UserAddress> userAddress = userAddressRepository.findByUserAdressIdUsdrId(id);
    if(userAddress.isPresent()){
    NullUtils.updateIfChanged(userAddress.get()::setUsdrAddress1, userPost.getAddress1(), userAddress.get()::getUsdrAddress1);
    NullUtils.updateIfChanged(userAddress.get()::setUsdrAdress2, userPost.getAddress2(), userAddress.get()::getUsdrAdress2);
    Cities city = cityRepository.findByCityName(userPost.getCity());
    userAddress.get().setCity(city);
  }


    User userSaved = save(user);
    return userSaved;
  }

}
