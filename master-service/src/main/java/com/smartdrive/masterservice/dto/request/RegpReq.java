package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegpReq {
    @Size(max = 3, message = "Region Plat Name Length Exceeded !")
    @NotNull(message = "Region Plat Name Cannot Be Null !")
    private String regpName;

    @Size(max = 35, message = "Region Plat Description Length Exceeded !")
    @NotNull(message = "Region Plat Description Cannot Be Null !")
    private String regpDesc;

    @NotNull(message = "Province ID Cannot Be Null !")
    private Long regpProvId;
}
