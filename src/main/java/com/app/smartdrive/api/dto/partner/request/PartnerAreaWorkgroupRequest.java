package com.app.smartdrive.api.dto.partner.request;

import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerAreaWorkgroupRequest {
    private Long cityId;
    private String areaWorkgroupId;
    private PartnerContactEntityId partnerContactId;
}
