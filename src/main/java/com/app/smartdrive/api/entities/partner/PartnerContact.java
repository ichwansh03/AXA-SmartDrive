package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "partner_contacts", schema = "partners")
public class PartnerContact {
    @EmbeddedId
    private PartnerContactEntityId id;
    @Column(name = "paco_status")
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status status;

}
