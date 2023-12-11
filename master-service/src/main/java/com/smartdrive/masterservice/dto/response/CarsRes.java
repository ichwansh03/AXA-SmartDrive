package com.smartdrive.masterservice.dto.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarsRes {
    private Long carsId;

    @Size(max = 55, message = "Cars Name Length Exceeded !")
    private String carsName;

    private int carsPassenger;
    private CarmRes carModel;
}
