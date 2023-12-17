package com.smartdrive.serviceorderservice.dto.request;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderReqDto {

    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    @NotNull @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private String seroReason;
    private String servClaimNo;
    private LocalDateTime servClaimStartdate;
    private LocalDateTime servClaimEnddate;
//    private ServiceOrderRespDto parentServiceOrders;
//    @NotNull
//    private ServiceRespDto services;
//    private EmployeesDto employees;
//    private ArwgRes areaWorkGroup;
}
