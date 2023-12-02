package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
  private String userName;
  private String userPassword;
  private String userFullName;
  private String userEmail;
  private String userBirthPlace;
  private LocalDateTime userBirthDate;
  private String userNationalId;
  private String userNpwp;
}
