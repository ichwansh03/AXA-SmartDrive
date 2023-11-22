package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "partner_contacts", schema = "partners")
public class PartnerContact {
    @EmbeddedId
    private PartnerContactEntityId id;
    @Column(name = "paco_status")
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status status;

    {
        status = EnumClassHR.status.ACTIVE;
    }

    @ManyToOne
    @JoinColumn(name = "paco_patrn_entityid", insertable = false, updatable = false)
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "paco_user_entityid", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "partnerContact")
    private List<PartnerAreaWorkgroup> partnerAreaWorkgroupList;

    public PartnerContactDto convertToDto(){
        List<PartnerAreaWorkgroupDto> partnerAreaWorkgroupDtoList = null;
        if(Objects.nonNull(partnerAreaWorkgroupList)){
            partnerAreaWorkgroupDtoList = partnerAreaWorkgroupList.stream().map(data -> data.convertToDto()).toList();
        }

        return PartnerContactDto.builder()
                .id(id)
                .status(status.name())
                .partnerName(partner.getPartName())
                .user(user)
                .partnerAreaWorkgroupList(partnerAreaWorkgroupDtoList)
                .build();
    }

}
