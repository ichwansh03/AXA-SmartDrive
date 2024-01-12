package com.app.smartdrive.api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
  @NotBlank(message = "Username may not be blank")
  private String userName;
  @NotBlank(message = "Name may not be blank")
  private String userFullName;
  @NotBlank(message = "Birth place may not be blank")
  private String userBirthPlace;
  @NotBlank
  @Email
  private String userEmail;
}
