package com.app.smartdrive.api.dto.payment.Request.Banks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BanksDtoRequests {
    @NotBlank(message = "Tidak Boleh Kosong")
    private String bank_name;
    @NotBlank(message = "Tidak Boleh Kosong" )
    private String bank_desc;
    
}
