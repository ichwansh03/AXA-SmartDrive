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
@AllArgsConstructor
@NoArgsConstructor
public class ServSeroDto {

    private String seroId;
    private EnumModuleServiceOrders.CreqType servType;
    private LocalDateTime servCreatedOn;
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private String userName;
    private String empName;
    private List<SoTasksDto> serviceOrderTasksList;

}
