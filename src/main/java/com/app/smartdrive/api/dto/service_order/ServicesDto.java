package com.app.smartdrive.api.dto.service_order;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ServicesDto {

    private String seroId;
    private String servType;
    private String servInsuranceNo;
    private LocalDate servCreatedOn;
    private EnumModuleServiceOrders.ServStatus servStatus;
    private String empName;
    private String userName;
    List<SoTasksDto> serviceOrderTasksList;
    public ServicesDto(String seroId, String servType, String servInsuranceNo, LocalDate servCreatedOn, EnumModuleServiceOrders.ServStatus servStatus, String empName, String userName) {
        this.seroId = seroId;
        this.servType = servType;
        this.servInsuranceNo = servInsuranceNo;
        this.servCreatedOn = servCreatedOn;
        this.servStatus = servStatus;
        this.empName = empName;
        this.userName = userName;
    }
}
