package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemiReq {
    @Size(max = 256, message = "Insurance Premi Name Length Exceeded")
    @NotNull(message = "Insurance Premi Name Cannot Be Null !")
    private String temiName;

    private Double temiRateMin;
    private Double temiRateMax;
    private Double temiNominal;

    @Size(max = 15, message = "Insurance Premi Name Length Exceeded")
    private String temiType;

    @NotNull(message = "Zones ID Cannot Be Null !")
    private Long temiZonesId;

    @Size(max = 25, message = "Insurance Type Name Length Exceeded")
    @NotNull(message = "Insurance Name Cannot Be Null !")
    private String temiIntyName;

    @NotNull(message = "Category ID Cannot Be Null !")
    private Long temiCateId;
}
