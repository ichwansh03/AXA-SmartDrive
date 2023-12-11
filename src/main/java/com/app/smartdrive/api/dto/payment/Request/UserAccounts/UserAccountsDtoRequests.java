package com.app.smartdrive.api.dto.payment.Request.UserAccounts;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAccountsDtoRequests {
    @NotBlank(message = "Tidak Boleh Kosong")
    private Double usac_debet;
    @NotBlank(message = "Tidak Boleh Kosong")
    private Double usac_credit;
}
