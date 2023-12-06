package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paco_patrn_entityid", insertable = false, updatable = false)
    private Partner partner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paco_user_entityid", insertable = false, updatable = false)
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "partnerContact", fetch = FetchType.LAZY)
    private List<PartnerAreaWorkgroup> partnerAreaWorkgroupList;

}
