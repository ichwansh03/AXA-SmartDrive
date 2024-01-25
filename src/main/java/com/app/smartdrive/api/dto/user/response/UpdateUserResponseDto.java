package com.app.smartdrive.api.dto.user.response;

import com.app.smartdrive.api.dto.user.UpdateUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponseDto extends UpdateUserDto {
  @NotNull(message = "Birth date may not be null")
  @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
  private LocalDateTime userBirthDate;
}
