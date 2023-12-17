package com.smartdrive.serviceorderservice.dto.request;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
