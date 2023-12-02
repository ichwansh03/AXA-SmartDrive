package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.dto.HR.request.CreateEmployeesDto;
import com.app.smartdrive.api.dto.master.AreaWorkGroupDto;
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
    private CreateEmployeesDto employees;
    private AreaWorkGroupDto areaWorkGroupDto;
    private Stream<SoTasksDto> soTasksDtoStream;
}
