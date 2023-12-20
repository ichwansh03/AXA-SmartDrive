package com.smartdrive.userservice.dto.response;

import com.smartdrive.userservice.entities.EnumUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleIdDto {
  private EnumUsers.RoleName usroRoleName;
}
