package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceTypeDto {
    @Size(max = 25, message = "Region Plat Name Length Exceeded !")
    private String intyName;

    @Size(max = 55, message = "Insurance Type Description Length Exceeded !")
    private String intyDesc;

    private List<TemplateInsurancePremiDto> templateInsurancePremiDto;
}
