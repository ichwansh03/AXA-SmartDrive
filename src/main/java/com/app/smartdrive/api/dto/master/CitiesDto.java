package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CitiesDto {
    private Long cityId;

    @Size(max = 85, message = "City Name Length Exceeded !")
    @NotBlank(message = "City Name Cannot Be Null")
    private String cityName;

    private Long cityProvId;
}
