package com.app.smartdrive.api.dto.partner;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {

    private Long partEntityid;

    @Size(max = 25)
    private String partName;

    @Size(max = 255)
    private String partAddress;

    private String partJoinDate;

    @Size(max = 35)
    private String partAccountNo;

    @Size(max = 25)
    private String partNpwp;

    private String partStatus;

    private String partModifiedDate;

    private Long businessEntity;

    private String city;

    private List<PartnerContactDto> partnerContactsDto;
}

