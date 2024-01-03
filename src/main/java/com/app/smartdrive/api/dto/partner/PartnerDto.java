package com.app.smartdrive.api.dto.partner;

import com.app.smartdrive.api.dto.master.response.CitiesRes;
import com.app.smartdrive.api.dto.user.response.BussinessEntityResponseDTO;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private BussinessEntityResponseDTO businessEntity;

    private CitiesRes city;

    @JsonManagedReference
    private List<PartnerContactDto> partnerContacts;
}

