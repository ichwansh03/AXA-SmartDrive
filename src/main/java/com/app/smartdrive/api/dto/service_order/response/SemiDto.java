package com.app.smartdrive.api.dto.service_order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemiDto {

    private Long semiServId;
    private BigDecimal semiPremiDebet;
    private BigDecimal semiPremiCredit;
    private String semiPaidType;
    private String semiStatus;
    private LocalDateTime semiModifiedDate;
    private List<SecrDto> secrDtoList;
}
