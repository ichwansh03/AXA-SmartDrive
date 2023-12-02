package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitiesDto {
    private Long cityId;

    @Size(max = 85, message = "City Name Length Exceeded !")
    private String cityName;

    private List<ProvinsiDto> provinsi;
}
