package com.app.smartdrive.api.dto.payment.Request.Fintech;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FintechDtoRequests {
    @NotBlank(message = "Tidak Boleh Kosong")
    private String fint_name;
    @NotBlank(message = "Tidak Boleh Kosong")
    private String fint_desc;
}
