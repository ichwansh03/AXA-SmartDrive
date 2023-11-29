package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuexResponseDTO {
    private Long cuexId;

    private Long cuexCreqEntityid;

    private String cuexName;

    private int cuexTotalItem;

    private double cuex_nominal;
}
