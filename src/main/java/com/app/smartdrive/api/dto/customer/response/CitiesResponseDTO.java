package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitiesResponseDTO {
    private Long cityId;

    private String cityName;

    private String provName;
}
