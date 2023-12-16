package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderRespDto {

    private String seroId;
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private String seroReason;
    private String servClaimNo;
    private LocalDateTime servClaimStartDate;
    private LocalDateTime servClaimEndDate;
    private ServiceRespDto services;
    private EmployeesAreaWorkgroupResponseDto employees;
    private PartnerDto partner;
    private List<SoTasksDto> soTasksDtoList;
}
