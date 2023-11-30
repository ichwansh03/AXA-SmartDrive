package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.*;

import java.util.stream.Stream;

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
    private String servClaimStartDate;
    private String servClaimEndDate;
    private Long seroServId;
    private Long seroAgentEntityid;
    private String seroArwgCode;
    private Stream<SoTasksDto> soTasksDtoStream;
}
