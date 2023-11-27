package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModelDto {
    private Long carmId;

    @Size(max = 55, message = "Car Model Name Length Exceeded !")
//    @NotBlank(message = "Car Model Name Cannot Be Null !")
    private String carmName;

    private Long carmCarbId;
}
