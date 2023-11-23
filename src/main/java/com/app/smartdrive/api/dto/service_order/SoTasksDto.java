package com.app.smartdrive.api.dto.service_order;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoTasksDto {

    private Long taskId;
    private String taskName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private EnumModuleServiceOrders.SeotStatus status;
    private List<SoWorkorderDto> serviceOrderWorkorder;
}
