package com.app.smartdrive.api.dto.customer.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiasDTO {

    @Size(max = 15, message = "test dari dto")
    private String ciasPoliceNumber;

    @Size(max = 4, message = "test dari dto")
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
