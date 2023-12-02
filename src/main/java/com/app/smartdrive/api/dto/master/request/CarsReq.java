package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarsReq {
    @Size(max = 55, message = "Cars Name Length Exceeded !")
    @NotNull(message = "Car Series Name Cannot Be Null !")
    private String carsName;

    @NotNull(message = "Car Series Passenger Cannot Be Null !")
    private int carsPassenger = 0;

    @NotNull(message = "Car Model ID Cannot Be Null !")
    private Long carsCarmId;
}
