package com.app.smartdrive.api.dto.customer.response;

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


    private IntyResponseDTO intyResponseDTO;

    private CarSeriesResponseDTO carSeriesResponseDTO;


    private CitiesResponseDTO city;
}
