package com.app.smartdrive.api.dto.customer.response;

import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.dto.master.CarSeriesDto;
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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    private List<CadocResponseDTO> cadocResponseDTOList;
    
    private List<CuexResponseDTO> cuexResponseDTOList;

    private CarSeriesDto carSeriesDto;

    private InsuranceTypeDto insuranceTypeDto;

    private String city;

}
