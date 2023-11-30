package com.app.smartdrive.api.dto.payment.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BanksDto {
    private Long bank_entityid;
    private String bank_name;
    private String bank_desc;
}
