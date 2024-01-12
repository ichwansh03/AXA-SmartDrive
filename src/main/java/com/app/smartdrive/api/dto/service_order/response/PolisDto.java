package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PolisDto {

    private Long creqId;
    private String seroId;
    private String customerName;
    private String polisNo;
    private String periode;
    private String vehicleNo;
    private BigDecimal totalPremi;
    private EnumCustomer.CreqType servType;
}
