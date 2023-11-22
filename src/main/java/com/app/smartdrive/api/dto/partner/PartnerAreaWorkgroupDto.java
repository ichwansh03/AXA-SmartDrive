package com.app.smartdrive.api.dto.partner;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerAreaWorkgroupDto {

    private PartnerAreaWorkGroupId id;
    private String status;
    private String modifiedDate;
    private AreaWorkGroup areaWorkGroup;
    private String partnerContact;
}
