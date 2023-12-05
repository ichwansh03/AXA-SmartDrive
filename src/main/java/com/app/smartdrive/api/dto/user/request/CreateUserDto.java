package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import lombok.Data;

@Data
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
  @NotEmpty(message = "Account may not be empty")
  private List<UserUserAccountDto> userAccounts;

  // private String userPhoto;
  // private MultipartFile photo; //terpisah
}
