package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.entities.*;
import com.smartdrive.userservice.repositories.RolesRepository;
import com.smartdrive.userservice.repositories.UserRoleRepository;
import com.smartdrive.userservice.services.UserRolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRolesImpl implements UserRolesService {
  private final RolesRepository rolesRepository;

  private final UserRoleRepository userRoleRepository;
  
  @Override
  public List<UserRoles> createUserRole(EnumUsers.RoleName roleName, User user) {
    UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), roleName);

    Roles roles = rolesRepository.findById(roleName).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus("ACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());
    userRoles.setUser(user);
    
    List<UserRoles> listRole = new ArrayList<>();
    listRole.add(userRoles);
    user.setUserRoles(listRole);
    return listRole;
  }

  @Override
  @Transactional
  public List<UserRoles> createUserRoleByAgen(EnumUsers.RoleName roleName, User user, Boolean isActive) {
    UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), roleName);

    String roleStatus;

    if(isActive){
      roleStatus = "ACTIVE";
    }else{
      roleStatus = "INACTIVE";
    }

    Roles roles = rolesRepository.findById(roleName).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus(roleStatus);
    userRoles.setUsroModifiedDate(LocalDateTime.now());
    userRoles.setUser(user);

    List<UserRoles> listRole = new ArrayList<>();
    listRole.add(userRoles);
    user.setUserRoles(listRole);
    return listRole;
  }

  @Override
  public User updateRoleFromPcToCu(User customer) {
    Optional<UserRoles> optionalUserRoles = this.userRoleRepository.checkRolePcIsExist(customer.getUserEntityId());

    if(optionalUserRoles.isPresent()){
      this.userRoleRepository.updateUserRoleTOCu(customer.getUserEntityId());

      for (UserRoles usro: customer.getUserRoles()){
        if(usro.getRoles().getRoleName() == EnumUsers.RoleName.PC){
          usro.getUserRolesId().setUsroRoleName(EnumUsers.RoleName.CU);
        }
      }

      log.info("UserRolesImpl::change user role from PC to CU");
      return customer;
    }else {
      return customer;
    }
  }

}
