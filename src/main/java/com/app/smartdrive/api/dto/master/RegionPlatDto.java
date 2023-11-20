package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionPlatDto {
    @Size(max = 3, message = "Region Plat Name Length Exceeded !")
    @NotBlank(message = "Region Plat Name Cannot Be Null")
    private String regp_name;

    private Long regp_prov_id;
}
