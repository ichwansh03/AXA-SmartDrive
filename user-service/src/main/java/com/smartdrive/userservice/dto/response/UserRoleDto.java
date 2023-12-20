package com.smartdrive.userservice.dto.response;

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
