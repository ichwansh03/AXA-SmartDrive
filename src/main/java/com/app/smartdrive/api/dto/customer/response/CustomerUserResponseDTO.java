package com.app.smartdrive.api.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerUserResponseDTO {
    private Long userEntityId;

    private String userName;

    private String userFullName;

    private String userEmail;

    private String userBirthPlace;

    private LocalDateTime userBirthDate;

    private String userNationalId;

    private String userNPWP;

    private String userPhoto;

    private LocalDateTime userModifiedDate;

    private List<UserPhoneResponseDTO> userPhone;
    private List<UserAddressResponseDTO> userAddresses;
//    private List<UserAccounts> userAccounts;

}
