package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import lombok.Data;

@Data

public class CreateUserDto {
  private ProfileRequestDto profile;
  private Long cityId;
  private Long bankId;
  private Long fintechId;
  private List<UserPhoneDto> userPhone;
  private List<UserAddressDto> userAddress;
  private List<UserUserAccountDto> userAccounts;

  // private String userPhoto;
  // private MultipartFile photo; //terpisah
}
