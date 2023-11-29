package com.app.smartdrive.api.dto.user;

import java.util.List;

import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
  private String userPhoto;
  private String fullName;
  private List<String> role;
  private String userEmail;
  private List<UserPhone> userPhone;
  private List<UserAddress> userAddresses;
  private List<UserAccounts> userAccounts;

}
