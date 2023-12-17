package com.smartdrive.serviceorderservice.dto.response;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
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
    private List<SoTasksDto> soTasksDtoList;
}
