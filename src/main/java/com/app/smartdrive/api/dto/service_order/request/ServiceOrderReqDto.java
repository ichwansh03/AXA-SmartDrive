package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class ServiceOrderReqDto {

    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    @NotNull @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private String seroReason;
    private String servClaimNo;
    private LocalDateTime servClaimStartdate;
    private LocalDateTime servClaimEnddate;
}
