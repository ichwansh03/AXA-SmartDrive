package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class PartnerContactEntityId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "paco_patrn_entityid", nullable = false, updatable = false)
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "paco_user_entityid", nullable = false, updatable = false)
    private User user;
}
