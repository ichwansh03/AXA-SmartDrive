package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;

public interface UserRolesService {
  List<UserRoles> createUserRole(RoleName roleName, User user);
}
