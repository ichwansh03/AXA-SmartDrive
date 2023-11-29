package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long servCustEntityid;
    private Long servCreqEntityid;

}
