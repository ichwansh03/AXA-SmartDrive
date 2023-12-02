package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.user.response.BussinessEntityResponseDTO;
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

    private BussinessEntityResponseDTO bussinessEntity;
        
    private LocalDateTime creqCreateDate;
    
    private EnumCustomer.CreqStatus creqStatus;

    private EnumCustomer.CreqType creqType;

    private LocalDateTime creqModifiedDate;

    private CustomerUserResponseDTO customer;
    
    private CustomerClaim customerClaim;

    private CiasResponseDTO customerInscAssets;

    private CustomerUserResponseDTO employee;

    private EmployeeAreaWorkgroupDto employeeAreaWorkgroup;
}
