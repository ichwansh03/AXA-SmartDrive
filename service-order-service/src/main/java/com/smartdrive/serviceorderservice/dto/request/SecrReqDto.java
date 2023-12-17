package com.smartdrive.serviceorderservice.dto.request;

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
    private BigDecimal secrPremiDebet;
    private BigDecimal secrPremiCredit;
    private LocalDateTime secrTrxDate;
    private LocalDateTime secrDueDate;
    //private String secrPatrTrxno;
}
