package com.app.smartdrive.api.dto.service_order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaspDto {

    private Long caspId;
    private String caspItemName;
    private Integer caspQuantity;
    private Double caspItemPrice;
    private Double caspSubtotal;
}
