package com.app.smartdrive.api.dto.user.response;

import com.app.smartdrive.api.entities.users.EnumUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleIdDto {
  private EnumUsers.roleName usroRoleName;
}
