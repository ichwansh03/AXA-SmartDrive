package com.app.smartdrive.api.dto.service_order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemiReqDto {

    private BigDecimal semiPremiDebet;
    private BigDecimal semiPremiCredit;
    private String semiPaidType;
    private String semiStatus;
    private LocalDateTime semiModifiedDate;

}
