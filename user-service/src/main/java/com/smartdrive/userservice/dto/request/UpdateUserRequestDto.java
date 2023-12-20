package com.smartdrive.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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