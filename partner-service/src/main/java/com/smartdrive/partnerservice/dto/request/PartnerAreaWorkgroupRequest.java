package com.smartdrive.partnerservice.dto.request;

import com.smartdrive.partnerservice.entities.PartnerContactEntityId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerAreaWorkgroupRequest {
    private Long cityId;
    private String areaWorkgroupId;
    private PartnerContactEntityId partnerContactId;
}
