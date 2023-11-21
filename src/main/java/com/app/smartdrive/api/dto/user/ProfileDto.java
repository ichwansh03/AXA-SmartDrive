package com.app.smartdrive.api.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
  private String userPhoto;
  private String userName;
  private String userFullName;
  private String userBirthPlace;
  private LocalDateTime userBirthDate;
}
