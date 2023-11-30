package com.app.smartdrive.api.entities.service_order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePremiCreditId implements Serializable {

    private Long secrId;

    private Long secrServId;
}
