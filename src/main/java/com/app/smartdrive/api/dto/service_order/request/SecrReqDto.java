package com.app.smartdrive.api.dto.service_order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class SecrReqDto {

    private Long secrId;
    private Long secrServId;
    private String secrYear;
    private BigDecimal secrPremiDebet;
    private BigDecimal secrPremiCredit;
    private LocalDateTime secrTrxDate;
    private LocalDateTime secrDueDate;
}
