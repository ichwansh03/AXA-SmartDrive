package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ZoneReq {
    @Size(max = 55, message = "Zone Name Length Exceeded !")
    @NotNull(message = "Zone Name Cannot Be Null !")
    private String zonesName;
}
