package com.app.smartdrive.api.dto.master.response;

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
    private Long carsCarmId;
    private CarmRes carModel;
}
