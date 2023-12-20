package com.smartdrive.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
  private String userName;
  private String userPassword;
  @NotBlank(message = "Name may not be blank")
  private String userFullName;
  @Email(message = "email must be valid")
  @NotBlank(message = "Email may not be blank")
  private String userEmail;

  private String userBirthPlace;

  private LocalDateTime userBirthDate;
  private String userByAgenBirthDate;
  @NotBlank(message = "NationalId may not be blank")
  private String userNationalId;
  @NotBlank(message = "NPWP may not be blank")
  private String userNpwp;
}
