package com.smartdrive.serviceorderservice.dto.response;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRespDto {

    private Long servId;
    private EnumModuleServiceOrders.CreqType servType;
    private String servInsuranceNo;
    private String servVehicleNumber;
    private LocalDateTime servCreatedOn;
    private LocalDateTime servStartDate;
    private LocalDateTime servEndDate;
    private EnumModuleServiceOrders.ServStatus servStatus;
    //private UserDto userDto;
    //private CustomerResponseDTO customerResponseDTO;
    private List<ServiceOrderRespDto> serviceOrdersList;
    private SemiDto semiDto;

}
