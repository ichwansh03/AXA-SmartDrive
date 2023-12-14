package com.app.smartdrive.api.dto.customer.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Long customerId;

    private Long employeeId;

    private Long agenId;

    @Valid
    @NotBlank
    private CiasDTO ciasDTO;
}
