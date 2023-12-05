package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderReqDto {

    @NotNull
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    @NotNull
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    @NotNull
    private String seroReason;
    private String servClaimNo;
    private LocalDateTime servClaimStartdate;
    private LocalDateTime servClaimEnddate;
    private Partner partner;
    private ServiceOrders parentServiceOrders;
    @NotNull
    private Services services;
    private Employees employees;
    private AreaWorkGroup areaWorkGroup;
}
