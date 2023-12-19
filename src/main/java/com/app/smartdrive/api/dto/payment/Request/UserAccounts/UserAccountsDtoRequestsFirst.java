package com.app.smartdrive.api.dto.payment.Request.UserAccounts;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAccountsDtoRequestsFirst {
    @NotBlank(message = "Tidak boleh kosong")
    private String noRekening;
}
