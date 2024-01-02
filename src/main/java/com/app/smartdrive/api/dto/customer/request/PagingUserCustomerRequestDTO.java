package com.app.smartdrive.api.dto.customer.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PagingUserCustomerRequestDTO extends BasePagingCustomerRequestDTO{
    @NotNull(message = "customer id must not null")
    private Long customerId;
}
