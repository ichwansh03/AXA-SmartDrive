package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandDto {
    private Long cabrID;

    @Size(max = 55, message = "Car Brand Name Length Exceeded !")
    private String cabrName;
}

