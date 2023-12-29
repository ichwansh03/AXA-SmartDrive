package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final EntityManager entityManager;

  @Override
  @Transactional
  public void updateUserRoleStatus(Long userEntityId, RoleName roleName, String newStatus) {
    UserRolesId userRolesId = new UserRolesId(userEntityId, roleName);

    UserRoles userRoles = userRoleRepository.findById(userRolesId)
            .orElseThrow(() -> new EntityNotFoundException("UserRoles not found with id: " + userRolesId));

    userRoles.setUsroStatus(newStatus);
    userRoles.setUsroModifiedDate(LocalDateTime.now());

    userRoleRepository.save(userRoles);
    entityManager.clear();
  }
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
  public void updateRoleFromPcToCu(Long userId) {
    UserRolesId userRolesId = new UserRolesId(userId, RoleName.PC);
    Optional<UserRoles> userRoles = userRoleRepository.findById(userRolesId);
    if(userRoles.isPresent()) {
      userRoleRepository.updateUserRoleTOCu(userId);
      entityManager.clear();
    }
  }
}
