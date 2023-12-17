package com.smartdrive.serviceorderservice.dto.request;

import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceReqDto {

    @NotNull
    private LocalDateTime servCreatedOn;
    @NotNull
    private EnumModuleServiceOrders.CreqType servType;
    @NotBlank
    private String servVehicleNumber;
    private LocalDateTime servStartDate;
    private LocalDateTime servEndDate;
    @NotNull
    private EnumModuleServiceOrders.ServStatus servStatus;
//    private Services parentServices;
//    @NotNull
//    private User users;
//    @NotNull
//    private CustomerRequest customer;

}
