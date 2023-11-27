package com.app.smartdrive.api.dto.partner.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerContactRequest {

    private Long partnerId;
    private String name;
    private String phone;
}
