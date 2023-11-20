package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ZonesDto {
    private Long zonesId;

    @Size(max = 55, message = "Zone Name Length Exceeded !")
    @NotBlank(message = "Zone Name Cannot Be Null !")
    private String zonesName;
}
