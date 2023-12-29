package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInscExtendResponseDTO {
    private Long cuexId;

    private Long cuexCreqEntityid;

    private String cuexName;

    private int cuexTotalItem;

    private BigDecimal cuexNominal;
}
