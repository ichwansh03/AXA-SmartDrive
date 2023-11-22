package com.app.smartdrive.api.dto.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.entities.users.User;
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
    private String status;
    private String partnerName;
    private User user;
    private List<PartnerAreaWorkgroupDto> partnerAreaWorkgroupList;
}

