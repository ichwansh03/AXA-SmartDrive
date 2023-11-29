package com.app.smartdrive.api.dto.customer.request;

import java.time.LocalDateTime;

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

    private Long cias_cars_id;

    private String cias_inty_name;

    private Long cias_city_id;

    private Double currentPrice;

    private Long[] cuexIds;
}
