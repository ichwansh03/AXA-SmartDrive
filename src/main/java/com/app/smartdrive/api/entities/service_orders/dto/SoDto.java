package com.app.smartdrive.api.entities.service_orders.dto;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_orders.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoDto {

    //seroId
    private String soName;
    //servClaimDate
    private LocalDate soCreatedOn;
    //servType
    private EnumModuleServiceOrders.ServType servType;
    //servStatus
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    //servClaimNo
    private String polisNumber;
    //customer
    private String customerName;
    //seroAgentEntityId
    private String financialAdvisor;
    //service order task list
    Set<ServiceOrderTasks> serviceOrderTasksSet;
}
