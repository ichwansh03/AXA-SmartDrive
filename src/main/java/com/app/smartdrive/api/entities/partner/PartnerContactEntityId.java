package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerContactEntityId implements Serializable {
    @Column(name = "paco_patrn_entityid", nullable = false)
    private Long partnerId;
    @Column(name = "paco_user_entityid", nullable = false)
    private Long userId;
}
