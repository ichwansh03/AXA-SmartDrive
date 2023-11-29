package com.app.smartdrive.api.dto.user;

import java.util.List;

import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private String userPhoto;
  private String userEmail;
  private List<UserPhone> userPhone;
  private List<UserAddress> userAddresses;
  private List<UserAccounts> userAccounts;

}
