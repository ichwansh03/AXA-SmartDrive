package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class PartnerContactEntityId implements Serializable {
    @Column(name = "paco_patrn_entityid", nullable = false)
    private Long partnerId;
    @Column(name = "paco_user_entityid", nullable = false)
    private Long userId;
}
