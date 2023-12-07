package com.app.smartdrive.api.dto.partner;

import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private UserDto user;

    @JsonManagedReference
    private List<PartnerAreaWorkgroupDto> partnerAreaWorkgroupList;
}

