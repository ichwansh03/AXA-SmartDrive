package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
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
    private EnumCustomer.CreqType servType;
    private String servInsuranceNo;
    private String servVehicleNumber;
    private LocalDateTime servStartDate;
    private LocalDateTime servEndDate;
    private EnumModuleServiceOrders.ServStatus servStatus;
    //private UserDto userDto;
    private CustomerResponseDTO customerResponseDTO;
    private List<ServiceOrderRespDto> serviceOrdersList;
    private SemiDto semiDto;

}
