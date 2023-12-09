package com.app.smartdrive.api.dto.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Long CreqCustEntityId;

    private Long agenId;

    private Long employeeId;

    private CiasDTO ciasDTO;
}
