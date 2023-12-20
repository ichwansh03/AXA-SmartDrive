package com.smartdrive.serviceorderservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingServiceOrder {

    private int page = 0;
    private int size = 3;
    private String type = "ALL";
    private String status = "OPEN";
    private String sortBy = "seroId";
    private String sort = "ascending";
}
