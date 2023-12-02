package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSeriesDto {
    private Long carsId;

    @Size(max = 55, message = "Cars Name Length Exceeded !")
    private String carsName;

    private int carsPassenger;
    private CarModelDto carModel;
}
