package com.smartdrive.serviceorderservice.dto.request;

import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ServiceTaskReqDto {

    @NotNull
    private String seotName;
    private LocalDateTime seotStartDate;
    private LocalDateTime seotEndDate;
    private LocalDateTime seotActualStartdate;
    private LocalDateTime seotActualEnddate;
    @NotNull
    private EnumModuleServiceOrders.SeotStatus seotStatus;
    //private AreaWorkGroup areaWorkGroup;
    @NotNull
    private ServiceOrders serviceOrders;
    private Object generateTasks;

    public ServiceTaskReqDto(String seotName, LocalDateTime seotActualStartdate, LocalDateTime seotActualEnddate, EnumModuleServiceOrders.SeotStatus seotStatus, ServiceOrders serviceOrders, Object generateTasks) {
        this.seotName = seotName;
        this.seotActualStartdate = seotActualStartdate;
        this.seotActualEnddate = seotActualEnddate;
        this.seotStatus = seotStatus;
        //this.areaWorkGroup = areaWorkGroup;
        this.serviceOrders = serviceOrders;
        this.generateTasks = generateTasks;
    }
}
