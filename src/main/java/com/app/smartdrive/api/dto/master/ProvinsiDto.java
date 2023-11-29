package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinsiDto {
    private Long provId;

    @Size(max = 85, message = "Province Name Length Exceeded !")
    private String provName;

    private Long provZonesId;
    private List<CitiesDto> citiesDto;
    private List<RegionPlatDto> regionPlatDto;
}
