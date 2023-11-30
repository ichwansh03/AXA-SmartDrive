package com.app.smartdrive.api.dto.service_order.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderReqDto {

    private String seroOrdtType;
    private String seroStatus;
    private String seroReason;
    private String servClaimNo;
}
