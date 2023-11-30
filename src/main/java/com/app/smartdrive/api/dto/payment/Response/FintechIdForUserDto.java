package com.app.smartdrive.api.dto.payment.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FintechIdForUserDto {
    private Long fint_entityid;
}
