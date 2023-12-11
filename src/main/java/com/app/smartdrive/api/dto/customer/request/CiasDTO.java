package com.app.smartdrive.api.dto.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiasDTO {

    private String ciasPoliceNumber;

    private String ciasYear;

    private String ciasStartdate;

    private Character ciasIsNewChar;

    private String ciasPaidType;

    private Long ciasCarsId;

    private Long ciasCityId;

    private String ciasIntyName;

    private Double currentPrice;

    private Long[] cuexIds;
}
