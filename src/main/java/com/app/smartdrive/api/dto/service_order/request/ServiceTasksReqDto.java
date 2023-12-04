package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ServiceTasksReqDto {

    private String seotName;
    private LocalDateTime seotStartDate;
    private LocalDateTime seotEndDate;
    private String seotStatus;
    private ServiceOrders serviceOrders;
    private AreaWorkGroup areaWorkGroup;
    private Object generatePolisTasks;

    public ServiceTasksReqDto(String seotName, LocalDateTime seotStartDate, LocalDateTime seotEndDate, ServiceOrders serviceOrders, AreaWorkGroup areaWorkGroup, Object generatePolisTasks) {
        this.seotName = seotName;
        this.seotStartDate = seotStartDate;
        this.seotEndDate = seotEndDate;
        this.serviceOrders = serviceOrders;
        this.areaWorkGroup = areaWorkGroup;
        this.generatePolisTasks = generatePolisTasks;
    }
}
