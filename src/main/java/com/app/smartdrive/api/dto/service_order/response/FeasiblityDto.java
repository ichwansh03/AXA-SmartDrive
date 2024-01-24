package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class FeasiblityDto {

    private Long creqId;
    private String customerName;
    private String customerEmail;
    private String createdOn;
    private String seroId;
    private EnumCustomer.CreqType servType;
    private EnumModuleServiceOrders.ServStatus servStatus;
}
