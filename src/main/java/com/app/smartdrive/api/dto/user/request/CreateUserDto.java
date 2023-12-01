package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserUserAccountDto;
import lombok.Data;

@Data
public class CreateUserDto {
  private String userName;
  private String userPassword;
  private String fullName;
  private String email;
  private String birthPlace;
  private String userBirthDate;
  private String userNationalId;
  private String userNpwp;
  private UserPhoneDto userPhone;
  private UserAddressDto userAddress;
  private UserUserAccountDto userAccount;
  // private String userPhoto;
  // private MultipartFile photo; //terpisah
}
