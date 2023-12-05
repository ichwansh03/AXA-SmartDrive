package com.app.smartdrive.api.services.users.implementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UpdateUserRequestDto;
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
import com.app.smartdrive.api.services.users.UserAddressService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.UserUserAccountService;
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
  private final UserRolesService userRolesService;
  private final UserPhoneService userPhoneService;
  private final UserAddressService userAddressService;
  private final UserUserAccountService userAccountService;

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
  public User createUserCustomer(CreateUserDto userPost) {
    User user = createUser(userPost.getProfile());

    userRolesService.createUserRole(RoleName.CU, user);

    userPhoneService.createUserPhone(user, userPost.getUserPhone());

    userAddressService.createUserAddress(user, userPost.getUserAddress(), userPost.getCityId());

    Long paymentId = (userPost.getBankId() != null) ? userPost.getBankId()
        : (userPost.getFintechId() != null) ? userPost.getFintechId() : null;

    userAccountService.createUserAccounts(userPost.getUserAccounts(), user, paymentId);


    // TODO USERPHOTO API
    // user.setUserPhoto(userPost.getPhoto().getOriginalFilename());
    // userPost.getPhoto().transferTo(new
    // File("C:\\Izhar\\SmartDrive-AXA\\src\\main\\resources\\image\\"+userPost.getPhoto().getOriginalFilename()));

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
  public UpdateUserRequestDto save(UpdateUserRequestDto userPost, Long id){
    User user = userRepo.findById(id).orElseThrow(
      () -> new EntityNotFoundException("User not found")
      );
    TransactionMapper.mapDtoToEntity(userPost, user);
    // String path = "src/test/resources/image/";
    // Path filePath = Paths.get(path+user.getUserPhoto());
    // Files.delete(filePath);
    save(user);
    UpdateUserRequestDto updated = TransactionMapper.mapEntityToDto(user, UpdateUserRequestDto.class);
    return updated;
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
