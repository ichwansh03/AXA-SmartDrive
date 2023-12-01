package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
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
  private final CityRepository cityRepository;
  private final UserRepository userRepo;
  private final BusinessEntityService businessEntityService;
  private final RolesRepository rolesRepository;
  private final BanksRepository banksRepository;
  private final FintechRepository fintechRepository;

  @Override
  public UserDto getByIdDto(Long id) {
    return TransactionMapper.mapEntityToDto(userRepo.findById(id).get(), UserDto.class);
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
  public User createUser(CreateUserDto userPost, RoleName roleName) throws Exception {
    BusinessEntity businessEntity = businessEntityService.createBusinessEntity();

    User newUser = new User();
    User user = TransactionMapper.mapDtoToEntity(userPost, newUser);

    user.setUserBusinessEntity(businessEntity);
    user.setUserEntityId(businessEntity.getEntityId());
    user.setUserModifiedDate(LocalDateTime.now());

    UserRolesId userRolesId = new UserRolesId(businessEntity.getEntityId(), roleName);

    Roles roles = rolesRepository.findById(roleName).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    List<UserRoles> listRole = List.of(userRoles);

    List<UserPhone> userPhone = user.getUserPhone();
    userPhone.forEach(phone -> {
      phone.setUser(user);
      phone.getUserPhoneId().setUsphEntityId(businessEntity.getEntityId());
      phone.setUsphModifiedDate(LocalDateTime.now());
    });

    List<UserAddress> userAddress = user.getUserAddress();
    userAddress.forEach(address -> {
      Cities city = cityRepository.findByCityName(userPost.getCityName()); // nanti by id
      address.setCity(city);
      address.setUsdrEntityId(businessEntity.getEntityId());
      address.setUsdrCityId(city.getCityId());
      address.setUsdrModifiedDate(LocalDateTime.now());
      address.setUser(user);
    });
    
    List<UserAccounts> userAccounts = user.getUserAccounts();
    for (int i = 0; i < userAccounts.size(); i++) {
      if(userAccounts.get(i).getEnumPaymentType().toString().equals("BANK")){
        Long idBank = userPost.getUserAccounts().get(i).getBankId();
        userAccounts.get(i).setBanks(banksRepository.findById(idBank).orElse(null));
        userAccounts.get(i).setUsacBankEntityid(idBank);
      }
      if(userAccounts.get(i).getEnumPaymentType().toString().equals("FINTECH")){
        Long idFintech = userPost.getUserAccounts().get(i).getFintechId();
        userAccounts.get(i).setFintech(fintechRepository.findById(idFintech).orElse(null));
        userAccounts.get(i).setUsacFintEntityid(idFintech);
      }
      userAccounts.get(i).setUser(user);
      userAccounts.get(i).setUsacUserEntityid(businessEntity.getEntityId());
    }

   
    // TODO USERPHOTO API
    // user.setUserPhoto(userPost.getPhoto().getOriginalFilename());
    // userPost.getPhoto().transferTo(new
    // File("C:\\Izhar\\SmartDrive-AXA\\src\\main\\resources\\image\\"+userPost.getPhoto().getOriginalFilename()));

    user.setUserRoles(listRole);
    userRoles.setUser(user);
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
    User user =
        userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")); // protect
                                                                                                // dto
    // NullUtils.updateIfChanged(user::setUserName, userPost.getUserName(), user::getUserName);
    // NullUtils.updateIfChanged(user::setUserFullName, userPost.getFullName(),
    // user::getUserFullName);
    // NullUtils.updateIfChanged(user::setUserBirthPlace, userPost.getBirthPlace(),
    // user::getUserBirthPlace);
    // NullUtils.updateIfChanged(user::setUserBirthDate,
    // LocalDateTime.parse(userPost.getUserBirthDate()),
    // user::getUserBirthDate);

    // NullUtils.updateIfChanged(user::setUserPassword, userPost.getUserPassword(),
    // user::getUserPassword);

    // NullUtils.updateIfChanged(user::setUserEmail, userPost.getEmail(), user::getUserEmail);
    // TODO USERPHOTO API
    // if(userPost.getPhoto() != null){
    // user.setUserPhoto(userPost.getPhoto().getOriginalFilename());
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
    if (user.isPresent()) {
      List<String> listRole = new ArrayList<>();
      for (UserRoles role : user.get().getUserRoles()) {
        listRole.add(role.getRoles().getRoleName().toString());
      }
      if (listRole.contains("PC") || listRole.contains("CU")) {
        if (user.get().getUserPassword().equals(password)) {
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
    if (user.isPresent()) {
      List<String> listRole = new ArrayList<>();
      for (UserRoles role : user.get().getUserRoles()) {
        listRole.add(role.getRoles().getRoleName().toString());
      }
      if (listRole.contains("EM")) {
        if (user.get().getUserPassword().equals(password)) {
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
