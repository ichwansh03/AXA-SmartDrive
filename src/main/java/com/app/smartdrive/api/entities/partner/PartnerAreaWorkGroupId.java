package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class PartnerAreaWorkGroupId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "pawo_arwg_code")
    private AreaWorkGroup areaWorkGroup;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "pawo_patr_entityid", referencedColumnName = "paco_patrn_entityid"),
            @JoinColumn(name = "pawo_user_entityid", referencedColumnName = "paco_user_entityid")
    })
    private PartnerContact partnerContact;
}
