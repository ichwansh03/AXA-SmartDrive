package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 12, message = "insurance number can't more than 12 character")
    private String servInsuranceNo;
    @NotBlank
    private String servVehicleNumber;
    @NotNull
    private LocalDateTime servStartDate;
    @NotNull
    private LocalDateTime servEndDate;
    @NotNull
    private EnumModuleServiceOrders.ServStatus servStatus;
    private Services parentServices;
    @NotNull
    private User users;
    @NotNull
    private CustomerRequest customer;

}
