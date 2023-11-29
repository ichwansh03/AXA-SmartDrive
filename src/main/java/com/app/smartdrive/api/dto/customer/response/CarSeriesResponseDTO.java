package com.app.smartdrive.api.dto.customer.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarSeriesResponseDTO {
    private Long carsId;
    private String carsName;
    private int carsPassenger;
}
