package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^(Category|Extend)$", message = "Invalid Insurance Premi Type")
    private String temiType;

    private Long temiZonesId;

    @Size(max = 25, message = "Insurance Name Length Exceeded !")
    private String temiIntyName;

    private Long temiCateId;
}
