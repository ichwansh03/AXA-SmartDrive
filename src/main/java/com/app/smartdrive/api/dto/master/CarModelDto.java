package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModelDto {
    private Long carmId;

    @Size(max = 55, message = "Car Model Name Length Exceeded !")
    private String carmName;

    private Long carmCarbId;
    private List<CarSeriesDto> carSeriesDto;
}
