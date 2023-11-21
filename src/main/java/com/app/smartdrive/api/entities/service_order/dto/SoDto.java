package com.app.smartdrive.api.entities.service_order.dto;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
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
@AllArgsConstructor
@NoArgsConstructor
public class SoDto {

    //seroId
    private String seroId;
    //servClaimDate
    private LocalDate servCreatedOn;
    //servType
    private String servType;
    //servStatus
    private EnumModuleServiceOrders.ServStatus servStatus;
    //servClaimNo
    private String noPolis;
    //customer
    private Long customerName;
    //seroAgentEntityId
    private Long empName;
    //service order task list
    List<SoTasksDto> serviceOrderTasksList;
}
