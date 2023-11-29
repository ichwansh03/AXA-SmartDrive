package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPhoneResponseDTO {

    private Long usphEntityId;

    private String usphPhoneNumber;

    private String usphPhoneType; //just HP or HOME

    private String usphMime;

    private String usphStatus;

    private LocalDateTime usphModifiedDate;
}
