package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeotPartnerDto {

    private Long partnerId;
    private Boolean repair;
    private Boolean sparepart;
    private EnumModuleServiceOrders.SeotStatus seotStatus;
}
