package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceTypeDto {
    @Size(max = 25, message = "Region Plat Name Length Exceeded !")
    @NotBlank(message = "Region Plat Name Cannot Be Null")
    private String intyName;

    @Size(max = 55, message = "Insurance Type Description Length Exceeded !")
    private String intyDesc;
}
