package com.app.smartdrive.api.dto.master;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSeriesDto {
    private Long carsId;

    @Size(max = 55, message = "Cars Name Length Exceeded !")
    @NotBlank(message = "Cars Name Cannot Be Null !")
    private String carsName;

    private int carsPassenger;

    private Long carsCarmId;
}
