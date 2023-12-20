package com.smartdrive.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessEntityDto {
  private Long entityId;
  private LocalDateTime entityModifiedDate;;
}
