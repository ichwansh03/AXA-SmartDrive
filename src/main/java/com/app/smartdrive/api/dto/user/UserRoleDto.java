package com.app.smartdrive.api.dto.user;

import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRoleDto {
  UserRoleIdDto userRolesId;
  String usroStatus;
}
