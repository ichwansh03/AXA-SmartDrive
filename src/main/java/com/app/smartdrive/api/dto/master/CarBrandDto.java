package com.app.smartdrive.api.dto.master;

import com.app.smartdrive.api.entities.master.CarModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandDto {
    private Long cabrID;

    @Size(max = 55, message = "Car Brand Name Length Exceeded !")
    @NotBlank(message = "Car Brand Name Cannot Be Null !")
    private String cabrName;
}

