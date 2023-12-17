package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimResponseDTO {

    private Long cuclCreqEntityId;

    private LocalDateTime cuclCreateDate;

    private int cuclEvents;

    private BigDecimal cuclEventPrice;

    private BigDecimal cuclSubtotal;

    private String cuclReason;
}
