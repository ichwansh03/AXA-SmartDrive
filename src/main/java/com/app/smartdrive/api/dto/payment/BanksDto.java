package com.app.smartdrive.api.dto.payment;

import lombok.Data;

@Data
public class BanksDto {
    private Long bank_entityid;
    private String bank_name;
    private String bank_desc;
}
