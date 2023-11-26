package com.app.smartdrive.api.dto.customer.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BussinessEntityResponseDTO {

  private Long entityId;

  private LocalDateTime entityModifiedDate;
}
