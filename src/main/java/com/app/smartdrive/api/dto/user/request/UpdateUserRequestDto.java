package com.app.smartdrive.api.dto.user.request;

import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.user.UpdateUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto extends UpdateUserDto {
  private LocalDateTime userBirthDate;
}