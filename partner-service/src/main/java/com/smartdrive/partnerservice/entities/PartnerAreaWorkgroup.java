package com.smartdrive.partnerservice.entities;

import com.smartdrive.partnerservice.entities.enums.EnumClassHR;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "partner_area_workgroup", schema = "partners")
@EntityListeners(AuditingEntityListener.class)
public class PartnerAreaWorkgroup {
    @EmbeddedId
    private PartnerAreaWorkGroupId id;
    @Column(name = "pawo_status")
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status status;

    {
        status = EnumClassHR.status.ACTIVE;
    }

    @LastModifiedDate
    @Column(name = "pawo_modified_date")
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "pawo_patr_entityid", referencedColumnName = "paco_patrn_entityid", insertable = false, updatable = false),
            @JoinColumn(name = "pawo_user_entityid", referencedColumnName = "paco_user_entityid", insertable = false, updatable = false)
    })
    private PartnerContact partnerContact;
}
