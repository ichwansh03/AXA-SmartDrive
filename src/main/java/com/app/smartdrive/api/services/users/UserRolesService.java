package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;

public interface UserRolesService {
  List<UserRoles> createUserRole(RoleName roleName, User user, boolean isActive);
  public User updateRoleFromPcToCu(User customer);
  public List<UserRoles> createUserRoleEmployees(RoleName roleName, User user, boolean isActive);
  public UserRoles updateUserRoleStatus(Long userEntityId, RoleName roleName, String newStatus);

  User updateRoleStatus(User customer, String active);
}
