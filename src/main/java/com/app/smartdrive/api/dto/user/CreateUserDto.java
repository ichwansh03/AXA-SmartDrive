package com.app.smartdrive.api.dto.user;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
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
