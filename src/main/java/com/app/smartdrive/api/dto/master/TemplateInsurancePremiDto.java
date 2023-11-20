package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemplateInsurancePremiDto {
    private Long temiId;

    @Size(max = 256, message = "Template Insurance Name Length Exceeded !")
    @NotBlank(message = "Template Insurance Cannot Be Null")
    private String temiName;

    private Double temiRateMin;

    private Double temiRateMax;

    private Double temiNominal;

    @Size(max = 0, message = "")
    @NotBlank(message = "")
    private String temiType;

    private Long temiZonesId;

    @Size(max = 25, message = "Insurance Name Length Exceeded !")
    @NotBlank(message = "Insurance Name Cannot Be Null !")
    private String temiIntyName;

    private Long temiCateId;
}
