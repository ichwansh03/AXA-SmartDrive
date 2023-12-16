package com.smartdrive.partnerservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartdrive.partnerservice.entities.PartnerContactEntityId;
import com.smartdrive.partnerservice.entities.enums.EnumClassHR;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerContactDto {

    private PartnerContactEntityId id;

    private EnumClassHR.status status;

    @JsonBackReference
    private PartnerDto partnerName;

    @JsonManagedReference
    private List<PartnerAreaWorkgroupDto> partnerAreaWorkgroupList;
}

