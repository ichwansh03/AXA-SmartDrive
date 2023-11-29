package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiasResponseDTO {

    private Long ciasCreqEntityid;

    private CustomerRequest customerRequest;


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
