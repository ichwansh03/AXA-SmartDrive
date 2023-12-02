package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemplateInsurancePremiDto {
    private Long temiId;

    @Size(max = 256, message = "Template Insurance Name Length Exceeded !")
    private String temiName;

    private Double temiRateMin;
    private Double temiRateMax;
    private Double temiNominal;
    private String temiType;
    private ZonesDto zones;
    private InsuranceTypeDto insuranceType;
    private CategoryDto category;
}
