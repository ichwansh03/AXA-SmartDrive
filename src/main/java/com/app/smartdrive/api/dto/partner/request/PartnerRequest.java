package com.app.smartdrive.api.dto.partner.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {
    @Size(max = 25)
    private String partName;
    @Size(max = 255)
    private String partAddress;
    @NotNull
    private Long cityId;
    @Size(max = 25)
    private String partNpwp;
    @NotNull
    private String partAccountNo;

    private boolean grantUserAccess;
}
