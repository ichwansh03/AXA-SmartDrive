package com.app.smartdrive.api.dto.customer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingAgenCustomerRequestDTO extends BasePagingCustomerRequestDTO{
    @NotNull(message = "employee id must not null")
    private Long employeeId;
    @NotBlank(message = "arwgCode must not blank")
    private String arwgCode;
}
