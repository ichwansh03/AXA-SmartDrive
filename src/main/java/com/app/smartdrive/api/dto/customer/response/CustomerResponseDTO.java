package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.response.BussinessEntityResponseDTO;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerResponseDTO {
    private Long creqEntityId;

    private BussinessEntityResponseDTO businessEntity;
        
    private LocalDateTime creqCreateDate;
    
    private EnumCustomer.CreqStatus creqStatus;

    private EnumCustomer.CreqType creqType;

    private LocalDateTime creqModifiedDate;

    private UserDto customer;

    private CiasResponseDTO customerInscAssets;

    private EmployeesAreaWorkgroupResponseDto employeeAreaWorkgroup;

    private ClaimResponseDTO customerClaim;

    private ServiceRespDto serviceRespDto;
}
