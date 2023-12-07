package com.app.smartdrive.api.dto.user.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessEntityDto {
  private Long entityId;
  private LocalDateTime entityModifiedDate;;
}
