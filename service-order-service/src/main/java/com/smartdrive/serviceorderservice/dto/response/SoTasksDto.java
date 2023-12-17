package com.smartdrive.serviceorderservice.dto.response;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
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

    private Long seotId;
    private String seotName;
    private LocalDateTime seotStartDate;
    private LocalDateTime seotEndDate;
    private EnumModuleServiceOrders.SeotStatus seotStatus;
    private List<SoWorkorderDto> serviceOrderWorkorders;
}
