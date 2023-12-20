package com.smartdrive.userservice.dto.request;

//import com.smartdrive.userservice.dto.UserUserAccountDto;
import com.smartdrive.userservice.dto.response.UserAddressDto;
import com.smartdrive.userservice.dto.response.UserPhoneDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

  @NotNull
  private ProfileRequestDto profile;
  @NotNull(message = "City may not be null")
  private Long cityId;
  private Long bankId;
  private Long fintechId;
  @NotEmpty(message = "userphone may not be empty")
  private List<UserPhoneDto> userPhone;
  @NotEmpty(message = "address may not be empty")
  private List<UserAddressDto> userAddress;
//  @NotEmpty(message = "Account may not be empty")
//  private List<UserUserAccountDto> userAccounts;

  // private String userPhoto;
  // private MultipartFile photo; //terpisah
}
