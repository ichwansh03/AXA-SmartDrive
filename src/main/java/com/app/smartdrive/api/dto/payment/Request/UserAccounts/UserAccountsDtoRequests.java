package com.app.smartdrive.api.dto.payment.Request.UserAccounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserAccountsDtoRequests extends UserAccountsDtoRequestsFirst {
    @NotNull(message = "Tidak Boleh Kosong")
    private Double nominall;
    
}
