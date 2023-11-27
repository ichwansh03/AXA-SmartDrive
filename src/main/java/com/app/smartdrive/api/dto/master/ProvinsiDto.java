package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinsiDto {
    private Long provId;

    @Size(max = 85, message = "Province Name Length Exceeded !")
    @NotBlank(message = "Province Name Cannot Be Null")
    private String provName;

    private Long provZonesId;
}
