package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
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
    private AreaWorkGroup areaWorkGroup;
    @NotNull
    private ServiceOrders serviceOrders;
    private Object generateTasks;

    public ServiceTaskReqDto(String seotName, LocalDateTime seotActualStartdate, LocalDateTime seotActualEnddate, EnumModuleServiceOrders.SeotStatus seotStatus, AreaWorkGroup areaWorkGroup, ServiceOrders serviceOrders, Object generateTasks) {
        this.seotName = seotName;
        this.seotActualStartdate = seotActualStartdate;
        this.seotActualEnddate = seotActualEnddate;
        this.seotStatus = seotStatus;
        this.areaWorkGroup = areaWorkGroup;
        this.serviceOrders = serviceOrders;
        this.generateTasks = generateTasks;
    }
}
