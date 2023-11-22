package com.app.smartdrive.api.dto.service_order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemiDto {

    private Long semiServId;
    private Double semiPremiDebet;
    private Double semiPremiCredit;
    private String semiPaidType;
    private String semiStatus;
    private LocalDate semiModifiedDate;
}
