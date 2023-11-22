package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionPlatDto {
    @Size(max = 3, message = "Region Plat Name Length Exceeded !")
    @NotBlank(message = "Region Plat Name Cannot Be Null !")
    private String regpName;

    @Size(max = 35, message = "Region Plat Desc Length Exceeded !")
    private String regpDesc;

    private Long regpProvId;
}
