package com.app.smartdrive.api.entities.service_order.dto;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoTasksDto {

    private Long taskId;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private EnumModuleServiceOrders.SeotStatus status;
    private ServiceOrderWorkorder serviceOrderWorkorder;
}
