package com.app.smartdrive.api.entities.service_order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePremiCreditId {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secr_id", nullable = false)
    private Long secrId;

    @Column(name = "secr_serv_id", nullable = false)
    private Long secrServId;
}
