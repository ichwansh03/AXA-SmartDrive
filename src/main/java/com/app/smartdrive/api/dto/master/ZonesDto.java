package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonesDto {
    private Long zonesId;

    @Size(max = 55, message = "Zone Name Length Exceeded !")
    private String zonesName;
}
