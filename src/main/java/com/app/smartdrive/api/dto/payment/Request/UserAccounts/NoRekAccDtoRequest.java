package com.app.smartdrive.api.dto.payment.Request.UserAccounts;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoRekAccDtoRequest {
    @NotBlank
    private String noRekening;
}
