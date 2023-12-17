package com.smartdrive.serviceorderservice.entities;

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
