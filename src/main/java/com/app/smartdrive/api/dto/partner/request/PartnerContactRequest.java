package com.app.smartdrive.api.dto.partner.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerContactRequest {

    @NotNull
    private Long partnerId;

    @Size(max = 85)
    private String name;

    private String phone;

    private boolean grantUserAccess;
}
