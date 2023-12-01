package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.mapper.user.UserMapper;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
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
  private final EntityManager entityManager;
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final RolesRepository rolesRepository;
  private final CityRepository cityRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;

  @Override
  public UserDto getByIdDto(Long id) {
    return UserMapper.convertUserToDto(userRepo.findById(id).get());
  }

  @Override
  public List<UserDto> getAllDto() {
    List<User> users = userRepo.findAll();
    List<UserDto> userDto = new ArrayList<>();
    for (User user : users) {
      userDto.add(UserMapper.convertUserToDto(user));
    }
    return userDto;
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    userRepo.deleteById(id);
  }

  @Override
  @Transactional
  public User create(CreateUserDto userPost) throws Exception{
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setEntityModifiedDate(LocalDateTime.now());
    Long businessEntityId = businessEntityService.save(businessEntity);

    User user = new User();
    user.setUserBusinessEntity(businessEntity);
    user.setUserEntityId(businessEntityId);
    user.setUserName(userPost.getUserName());
    user.setUserPassword(userPost.getUserPassword()); //Encrypt
    user.setUserFullName(userPost.getFullName());
    user.setUserEmail(userPost.getEmail());
    user.setUserBirthPlace(userPost.getBirthPlace());
    user.setUserBirthDate(LocalDateTime.parse(userPost.getUserBirthDate()));
    user.setUserNPWP(userPost.getUserNpwp());
    user.setUserNationalId(userPost.getUserNationalId() + businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());

    UserRolesId userRolesId = new UserRolesId(businessEntityId, roleName.PC);

    Roles roles = rolesRepository.findById(roleName.PC).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    List<UserRoles> listRole = List.of(userRoles);

    UserPhoneId userPhoneId = new UserPhoneId(businessEntityId, userPost.getUserPhone().getUserPhoneId().getUsphPhoneNumber());

    UserPhone userPhone = new UserPhone();
    userPhone.setUserPhoneId(userPhoneId);
    userPhone.setUsphPhoneType(userPost.getUserPhone().getUsphPhoneType());
    userPhone.setUsphModifiedDate(LocalDateTime.now());

    List<UserPhone> listPhone = List.of(userPhone);

    // Cities city = cityRepository.findByCityName(userPost.getUserAddress().getCity()); //Validate

    // Optional<UserAddress> findTopByOrderByIdDesc = userAddressRepository.findLastOptional();
    // Long lastIndexUsdr;
    // if (findTopByOrderByIdDesc.isPresent()) {
    //   lastIndexUsdr = findTopByOrderByIdDesc.get().getUserAdressId().getUsdrId();
    // } else {
    //   lastIndexUsdr = 1L;
    // }

    // UserAdressId userAdressId = new UserAdressId();
    // userAdressId.setUsdrId(lastIndexUsdr + 1); //tambahin sequence
    UserAddress userAddress = new UserAddress();
    userAddress.setUsdrEntityId(businessEntityId);
    // userAddress.setUsdrCityId(city.getCityId());
    // userAddress.setUserAdressId(userAdressId);
    // userAddress.setCity(city);
    userAddress.setUsdrAddress1(userPost.getUserAddress().getUsdrAddress1());
    userAddress.setUsdrAddress2(userPost.getUserAddress().getUsdrAddress2());
    userAddress.setUsdrModifiedDate(LocalDateTime.now());

    List<UserAddress> listAddress = List.of(userAddress);

    // UserAccounts userAccounts = new UserAccounts();
    // userAccounts.setUsac_accountno(userPost.getUserAccount().getUsac_accountno());
    // if (userPost.getUserAccount().getEnumPaymentType().equals("BANK")) { //urusan giry
    //   userAccounts.setEnumPaymentType(EnumPaymentType.BANK);
    //   Banks bank = banksRepository.findByBankNameOptional(userPost.getUserAccount().getBank())
    //       .orElseThrow(() -> new EntityNotFoundException("Bank not found"));
    //   userAccounts.setBanks(bank);
    //   userAccounts.setUsacBankEntityid(bank.getBank_entityid());
    // }
    // if (userPost.getUserAccount().getEnumPaymentType().equals("FINTECH")) {
    //   userAccounts.setEnumPaymentType(EnumPaymentType.FINTECH);
    //   Fintech fintech = fintechRepository.findByFintNameOptional(userPost.getUserAccount().getFintech())
    //       .orElseThrow(() -> new EntityNotFoundException("Fintech not found"));
    //   userAccounts.setFintech(fintech);
    // }
    // userAccounts.setUser(user);
    // userAccounts.setUsacUserEntityid(businessEntityId);
    // List<UserAccounts> listUserAccountuserAccounts = List.of(userAccounts);

    //TODO USERPHOTO API
    // user.setUserPhoto(userPost.getPhoto().getOriginalFilename());
    // userPost.getPhoto().transferTo(new File("C:\\Izhar\\SmartDrive-AXA\\src\\main\\resources\\image\\"+userPost.getPhoto().getOriginalFilename()));
 
    user.setUserPhone(listPhone);
    user.setUserRoles(listRole);
    user.setUserAddress(listAddress);
    // user.setUserAccounts(listUserAccountuserAccounts);
    // userAccounts.setUser(user);
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
    User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")); //protect dto
    NullUtils.updateIfChanged(user::setUserName, userPost.getUserName(), user::getUserName);
    NullUtils.updateIfChanged(user::setUserFullName, userPost.getFullName(), user::getUserFullName);
    NullUtils.updateIfChanged(user::setUserBirthPlace, userPost.getBirthPlace(),
        user::getUserBirthPlace);
    NullUtils.updateIfChanged(user::setUserBirthDate, LocalDateTime.parse(userPost.getUserBirthDate()),
        user::getUserBirthDate);

    NullUtils.updateIfChanged(user::setUserPassword, userPost.getUserPassword(), user::getUserPassword);

    NullUtils.updateIfChanged(user::setUserEmail, userPost.getEmail(), user::getUserEmail);
    // TODO USERPHOTO API
    // if(userPost.getPhoto() != null){
    //   user.setUserPhoto(userPost.getPhoto().getOriginalFilename());
    // }

    User userSaved = save(user);
    return userSaved;
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepo.findById(id);
  }

  @Override
  public String loginCu(String identity, String password) {
    Optional<User> user = userRepo.findUserByIden(identity);
    if(user.isPresent()){
      List<String> listRole = new ArrayList<>();
      for (UserRoles role : user.get().getUserRoles()) {
        listRole.add(role.getRoles().getRoleName().toString());
      } 
      if(listRole.contains("PC")||listRole.contains("CU")){
        if(user.get().getUserPassword().equals(password)){
          return "Access Granted";
        }
        return "Wrong Password";
      }
      return "Access Denied";
    }
    return "Input email atau username atau phoneNumber salah";
  }

  @Override
  public String loginEm(String identity, String password) {
    Optional<User> user = userRepo.findUserByIden(identity);
    if(user.isPresent()){
      List<String> listRole = new ArrayList<>();
      for (UserRoles role : user.get().getUserRoles()) {
        listRole.add(role.getRoles().getRoleName().toString());
      } 
      if(listRole.contains("EM")){
        if(user.get().getUserPassword().equals(password)){
        return "Access Granted";
        }
        return "Wrong Password";
      }
      return "Access Denied";
    }
    return "Input email atau username atau phoneNumber salah";
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
