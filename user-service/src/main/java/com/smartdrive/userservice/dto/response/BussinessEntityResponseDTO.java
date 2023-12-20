package com.smartdrive.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BussinessEntityResponseDTO {
    private Long entityId;

    private LocalDateTime entityModifiedDate;
}
