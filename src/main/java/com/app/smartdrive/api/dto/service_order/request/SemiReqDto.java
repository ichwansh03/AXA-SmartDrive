package com.app.smartdrive.api.dto.service_order.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemiReqDto {

    @NotNull
    private Long semiServId;
    private BigDecimal semiPremiDebet;
    private BigDecimal semiPremiCredit;
    private String semiPaidType;
    private String semiStatus;
    private LocalDateTime semiModifiedDate;

}
