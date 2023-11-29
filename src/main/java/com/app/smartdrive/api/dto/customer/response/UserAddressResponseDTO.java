package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.entities.users.UserAdressId;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddressResponseDTO {

    private Long usdrId;

    private Long usdrEntityId;

    private String usdrAddress1;

    private String usdrAdress2;

    private Long usdrCityId;

    private LocalDateTime usdrModifiedDate;
}
