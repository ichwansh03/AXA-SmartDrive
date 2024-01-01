package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceDto {

    private Long servId;
    private EnumCustomer.CreqType servType;
    private String servInsuranceNo;
    private String servVehicleNumber;
    private LocalDateTime servCreatedOn;
    private LocalDateTime servStartDate;
    private LocalDateTime servEndDate;
    private EnumModuleServiceOrders.ServStatus servStatus;
}
