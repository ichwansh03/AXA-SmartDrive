package com.app.smartdrive.api.dto.master.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonesRes {
    private Long zonesId;

    @Size(max = 55, message = "Zone Name Length Exceeded !")
    private String zonesName;
}
