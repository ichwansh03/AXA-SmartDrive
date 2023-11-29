package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserPhone;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
