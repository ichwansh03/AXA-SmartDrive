package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.master.response.CarsRes;
import com.app.smartdrive.api.dto.master.response.CitiesRes;
import com.app.smartdrive.api.dto.master.response.IntyRes;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiasResponseDTO {

    private Long ciasCreqEntityid;

    private String ciasPoliceNumber;

    private String ciasYear;

    private LocalDateTime ciasStartdate;

    private LocalDateTime ciasEnddate;

    private Double ciasCurrentPrice;

    private Double ciasInsurancePrice;

    private Double ciasTotalPremi;

    private EnumCustomer.CreqPaidType ciasPaidType;

    private Character ciasIsNewChar;

    private List<CadocResponseDTO> customerInscDoc;

    private List<CuexResponseDTO> customerInscExtend;

    private IntyRes insuranceType;

    private CarsRes carSeries;

    private CitiesRes city;
}
