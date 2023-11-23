package com.app.smartdrive.api.dto.partner.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {
    @Size(max = 25)
    private String name;
    @Size(max = 255)
    private String address;
    private String city;
    @Size(max = 25)
    private String npwp;
}
