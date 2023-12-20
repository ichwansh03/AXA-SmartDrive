package com.smartdrive.userservice.services;


import com.smartdrive.userservice.entities.EnumUsers;
import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.entities.UserRoles;

import java.util.List;

public interface UserRolesService {
  List<UserRoles> createUserRole(EnumUsers.RoleName roleName, User user);

  public List<UserRoles> createUserRoleByAgen(EnumUsers.RoleName roleName, User user, Boolean isActive);

  public User updateRoleFromPcToCu(User customer);
}
