package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.services.users.UserRolesService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRolesImpl implements UserRolesService{
  private final RolesRepository rolesRepository;
  
  @Override
  public List<UserRoles> createUserRole(RoleName roleName, User user) {
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
  
}
