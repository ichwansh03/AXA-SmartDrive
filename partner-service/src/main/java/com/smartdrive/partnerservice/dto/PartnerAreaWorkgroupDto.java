package com.smartdrive.partnerservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkGroupId;
import com.smartdrive.partnerservice.entities.enums.EnumClassHR;
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

    @JsonBackReference
    private PartnerContactDto partnerContact;
}
