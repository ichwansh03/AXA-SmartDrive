package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import lombok.*;

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
    private String servClaimStartDate;
    private String servClaimEndDate;
    private ServiceRespDto services;
    private EmployeesRequestDto employees;
    private ArwgRes arwgRes;
    private PartnerDto partner;
    private List<SoTasksDto> soTasksDtoList;
}
