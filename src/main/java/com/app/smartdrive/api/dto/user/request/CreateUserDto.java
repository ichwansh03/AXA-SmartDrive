package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import lombok.Data;

@Data
public class CreateUserDto {
  private String userName;
  private String userPassword;
  private String userFullName;
  private String userEmail;
  private String userBirthPlace;
  private LocalDateTime userBirthDate;
  private String userNationalId;
  private String userNpwp;
  private String cityName;
  private List<UserPhoneDto> userPhone;
  private List<UserAddressDto> userAddress;
  private List<UserUserAccountRequestDto> userAccounts;

  // private String userPhoto;
  // private MultipartFile photo; //terpisah
}
