package com.app.smartdrive.api.dto.master.response;

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

    private Long cityProvId;

    private ProvRes provinsi;
}
