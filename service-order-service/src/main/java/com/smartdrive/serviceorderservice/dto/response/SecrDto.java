package com.smartdrive.serviceorderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecrDto {

    private Long secrId;
    private Long secrServId;
    private String secrYear;
    private BigDecimal secrPremiDebet;
    private BigDecimal secrPremiCredit;
    private LocalDate secrTrxDate;
    private LocalDate secrDuedate;
}
