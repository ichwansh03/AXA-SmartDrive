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

    private String arwg_code;

    private Long agen_id;

    private CiasDTO ciasDTO;
}
