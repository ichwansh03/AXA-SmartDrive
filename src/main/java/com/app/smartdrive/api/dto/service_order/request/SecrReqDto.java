package com.app.smartdrive.api.dto.service_order.request;

import jakarta.validation.constraints.NotNull;
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
public class SecrReqDto {

    private Long secrServId;
    private String secrYear;
    private Double secrPremiDebet;
    private Double secrPremiCredit;
    private LocalDateTime secrTrxDate;
    private LocalDateTime secrDueDate;
    //private String secrPatrTrxno;
}
