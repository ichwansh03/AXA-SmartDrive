package com.app.smartdrive.api.dto.partner;

import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerAreaWorkgroupDto {

    private PartnerAreaWorkGroupId id;
    private EnumClassHR.status status;
    private LocalDateTime modifiedDate;
    private ArwgRes areaWorkGroup;
    @JsonBackReference
    private PartnerContactDto partnerContact;
}
