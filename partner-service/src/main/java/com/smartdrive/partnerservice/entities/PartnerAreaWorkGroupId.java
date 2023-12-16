package com.smartdrive.partnerservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class PartnerAreaWorkGroupId implements Serializable {
    @Column(name = "pawo_arwg_code", nullable = false)
    private String areaWorkGroup;
    @Column(name = "pawo_patr_entityid", nullable = false)
    private Long partnerId;
    @Column(name = "pawo_user_entityid", nullable = false)
    private Long userId;
}
