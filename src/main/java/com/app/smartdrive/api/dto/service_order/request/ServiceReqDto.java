package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
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
    private EnumCustomer.CreqType servType;
    @NotBlank
    private String servVehicleno;
    private String servInsuranceno;
    private LocalDateTime servStartdate;
    private LocalDateTime servEnddate;
    @NotNull
    private EnumModuleServiceOrders.ServStatus servStatus;
}
