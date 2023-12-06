package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
  private String userName;
  private String userPassword;
  @NotBlank(message = "Name may not be blank")
  private String userFullName;
  @Email(message = "email must be valid")
  @NotBlank(message = "Username may not be blank")
  private String userEmail;

  private String userBirthPlace;

  private LocalDateTime userBirthDate;
  @NotBlank(message = "NationalId may not be blank")
  private String userNationalId;
  @NotBlank(message = "NPWP may not be blank")
  private String userNpwp;
}
