package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
  private String userName;
  private String userFullName;
  private String userBirthPlace;
  private LocalDateTime userBirthDate;
}