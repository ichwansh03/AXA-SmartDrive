package com.app.smartdrive.api.dto.user.response;

import java.util.List;

import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
  private String userPhoto;
  private String userFullName;
  private List<UserRoleDto> userRoles;
  private String userEmail;
  private List<UserPhoneDto> userPhone;
  private List<UserAddressDto> userAddress;
  private List<UserUserAccountDto> userAccounts;

}
