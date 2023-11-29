package com.app.smartdrive.api.dto.service_order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecrDto {

    private Long secrId;
    private Long secrServId;
    private String secrYear;
    private Double secrPremiDebet;
    private Double secrPremiCredit;
    private LocalDate secrTrxDate;
    private LocalDate secrDuedate;
    private String secrPatrTrxno;
}
