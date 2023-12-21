package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.services.users.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRolesImpl implements UserRolesService{
  private final RolesRepository rolesRepository;

  private final UserRoleRepository userRoleRepository;

  @Override
  @Transactional
  public List<UserRoles> createUserRole(RoleName roleName, User user, boolean isActive) {
    UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), roleName);

    Roles roles = rolesRepository.findById(roleName)
            .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus(isActive ? "ACTIVE" : "INACTIVE");
    userRoles.setUsroModifiedDate(LocalDateTime.now());
    userRoles.setUser(user);

    List<UserRoles> listRole = new ArrayList<>();
    listRole.add(userRoles);
    user.setUserRoles(listRole);
    return listRole;
  }
  @Override
  public List<UserRoles> createUserRoleEmployees(RoleName roleName, User user, boolean isActive) {
    UserRolesId userRolesId = new UserRolesId(user.getUserEntityId(), roleName);

    Roles roles = rolesRepository.findById(roleName).get();
    UserRoles userRoles = new UserRoles();
    userRoles.setRoles(roles);
    userRoles.setUserRolesId(userRolesId);
    userRoles.setUsroStatus(isActive ? "ACTIVE" : "INACTIVE");
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
