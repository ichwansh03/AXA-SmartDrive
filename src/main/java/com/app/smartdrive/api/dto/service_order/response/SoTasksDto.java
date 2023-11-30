package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoTasksDto {

    private Long seotId;
    private String seotName;
    private LocalDateTime seotStartDate;
    private LocalDateTime seotEndDate;
    private EnumModuleServiceOrders.SeotStatus seotStatus;
    private Stream<SoWorkorderDto> serviceOrderWorkorders;
}
