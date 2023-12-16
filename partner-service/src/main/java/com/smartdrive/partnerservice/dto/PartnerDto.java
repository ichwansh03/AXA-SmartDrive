package com.smartdrive.partnerservice.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smartdrive.partnerservice.entities.enums.EnumClassHR;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  PartnerDto {

    private Long partEntityid;

    @Size(max = 25)
    private String partName;

    @Size(max = 255)
    private String partAddress;

    private LocalDateTime partJoinDate;

    @Size(max = 35)
    private String partAccountNo;

    @Size(max = 25)
    private String partNpwp;

    private EnumClassHR.status partStatus;

    private LocalDateTime partModifiedDate;

    @JsonManagedReference
    private List<PartnerContactDto> partnerContacts;
}

