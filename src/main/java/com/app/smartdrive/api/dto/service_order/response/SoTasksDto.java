package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoTasksDto {

    private Long seotId;
    private String seotName;

    private String seotActualStartdate;
    private String seotActualEnddate;
    private EnumModuleServiceOrders.SeotStatus seotStatus;
    private List<SoWorkorderDto> serviceOrderWorkorders;
}
