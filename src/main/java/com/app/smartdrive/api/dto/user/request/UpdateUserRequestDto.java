package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
  @NotBlank(message = "Username may not be blank")
  private String userName;
  @NotBlank(message = "Name may not be blank")
  private String userFullName;
  @NotBlank(message = "Birth place may not be blank")
  private String userBirthPlace;
  @NotNull(message = "Birth date may not be null")
  private LocalDateTime userBirthDate;
}