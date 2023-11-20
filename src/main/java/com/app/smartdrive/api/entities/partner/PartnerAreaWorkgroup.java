package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "partner_area_workgroup")
public class PartnerAreaWorkgroup {
    @EmbeddedId
    private PartnerAreaWorkGroupId id;
    @Column(name = "pawo_status")
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status status;
    @Column(name = "pawo_modified_date")
    private LocalDateTime modifiedDate;
}
