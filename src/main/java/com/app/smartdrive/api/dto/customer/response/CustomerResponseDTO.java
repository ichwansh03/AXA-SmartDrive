package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.response.BussinessEntityResponseDTO;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creqCreateDate;
    
    private EnumCustomer.CreqStatus creqStatus;

    private EnumCustomer.CreqType creqType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creqModifiedDate;

    private UserDto customer;

    private CustomerInscAssetsResponseDTO customerInscAssets;

    private EmployeesAreaWorkgroupResponseDto employeeAreaWorkgroup;

    private ClaimResponseDTO customerClaim;

    private ServiceRespDto serviceRespDto;
}
