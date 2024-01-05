package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.dto.master.response.CarsRes;
import com.app.smartdrive.api.dto.master.response.CitiesRes;
import com.app.smartdrive.api.dto.master.response.IntyRes;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInscAssetsResponseDTO {

    private Long ciasCreqEntityid;

    private String ciasPoliceNumber;

    private String ciasYear;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ciasStartdate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ciasEnddate;

    private BigDecimal ciasCurrentPrice;

    private BigDecimal ciasInsurancePrice;

    private BigDecimal ciasTotalPremi;

    private EnumCustomer.CreqPaidType ciasPaidType;

    private Character ciasIsNewChar;

    private List<CustomerInscDocResponseDTO> customerInscDoc;

    private List<CustomerInscExtendResponseDTO> customerInscExtend;

    private IntyRes insuranceType;

    private CarsRes carSeries;

    private CitiesRes city;
}
