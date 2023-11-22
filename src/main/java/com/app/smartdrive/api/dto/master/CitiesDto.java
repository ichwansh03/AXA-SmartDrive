package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitiesDto {
    private Long cityId;

    @Size(max = 85, message = "City Name Length Exceeded !")
    @NotBlank(message = "City Name Cannot Be Null")
    private String cityName;

    private Long cityProvId;
}
