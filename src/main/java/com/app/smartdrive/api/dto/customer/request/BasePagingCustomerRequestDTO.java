package com.app.smartdrive.api.dto.customer.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePagingCustomerRequestDTO {
    private int page = 0;
    private int size = 3;
    private String type = "ALL";
    private String status = "OPEN";
    private String sortBy = "creqEntityId";
    private String sort = "ascending";
}
