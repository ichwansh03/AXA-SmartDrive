package com.app.smartdrive.api.dto.customer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequestDTO {

    private Long creqEntityId;

    private Long customerId;

    private Long agenId;

    private Long employeeId;

    private Boolean accessGrantUser = true;

    private CiasDTO ciasDTO;
}
