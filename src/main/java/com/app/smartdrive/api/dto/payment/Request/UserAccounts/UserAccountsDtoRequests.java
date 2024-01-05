package com.app.smartdrive.api.dto.payment.Request.UserAccounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccountsDtoRequests  {
    private String noRekening;
    @NotNull(message = "Tidak Boleh Kosong")
    private BigDecimal nominall;
    
}
