package com.smartdrive.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Long userEntityId;
  private String userPhoto;
  private String userFullName;
  private List<UserRoleDto> userRoles;
  private String userEmail;
  private List<UserPhoneDto> userPhone;
  private List<UserAddressDto> userAddress;
//  private List<UserUserAccountResponseDto> userAccounts;

}
