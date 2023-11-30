    package com.app.smartdrive.api.dto.payment.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanksIdForUserDto {
    private Long bank_entity_id;
}
