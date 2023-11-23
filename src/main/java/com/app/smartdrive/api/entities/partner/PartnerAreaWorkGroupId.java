package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class PartnerAreaWorkGroupId implements Serializable {
    @Column(name = "pawo_arwg_code", nullable = false)
    private String areaWorkGroup;
    @Column(name = "pawo_patr_entityid", nullable = false)
    private Long partnerId;
    @Column(name = "pawo_user_entityid", nullable = false)
    private Long userId;
}
