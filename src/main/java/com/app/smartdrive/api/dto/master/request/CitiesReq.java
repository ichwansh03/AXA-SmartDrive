package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CitiesReq {
    @Size(max = 85, message = "City Name Length Exceeded !")
    @NotNull(message = "City Name Cannot Be Null !")
    private String cityName;

    @NotNull(message = "Province ID Cannot Be Null !")
    private Long cityProvId;
}
