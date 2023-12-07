package com.app.smartdrive.api.dto.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Long creq_cust_entityid;
    
    private String arwg_code;

    private Long agen_id;

    private CiasDTO ciasDTO;
}
