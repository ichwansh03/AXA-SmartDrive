package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeotPartnerDto {

    private Long partnerId;
    private boolean isRepair;
    private boolean isSparepart;
    private EnumModuleServiceOrders.SeotStatus seotStatus;
}
