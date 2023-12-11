package com.smartdrive.masterservice.dto.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitiesRes {
    private Long cityId;

    @Size(max = 85, message = "City Name Length Exceeded !")
    private String cityName;

    private ProvRes provinsi;
}
