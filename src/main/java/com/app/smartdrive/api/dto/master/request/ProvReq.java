package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProvReq {
    @Size(max = 85, message = "Province Name Length Exceeded !")
    @NotNull(message = "Provinsi Name Cannot Be Null !")
    private String provName;

    @NotNull(message = "Zone ID Cannot Be Null !")
    private Long provZonesId;
}
