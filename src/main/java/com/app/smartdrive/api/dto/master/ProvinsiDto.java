package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProvinsiDto {
    private Long provId;

    @Size(max = 85, message = "Province Name Length Exceeded !")
    @NotBlank(message = "Province Name Cannot Be Null")
    private String provName;

    private Long prov_zones_id;
}
