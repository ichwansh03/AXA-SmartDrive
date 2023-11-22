package com.app.smartdrive.api.entities.service_order.dto;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoTasksDto {

    private Long taskId;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private EnumModuleServiceOrders.SeotStatus status;
    private List<SoWorkorderDto> serviceOrderWorkorder;
}
