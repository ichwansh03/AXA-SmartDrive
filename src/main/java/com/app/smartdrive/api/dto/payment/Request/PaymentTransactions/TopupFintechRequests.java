package com.app.smartdrive.api.dto.payment.Request.PaymentTransactions;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopupFintechRequests {
    @NotBlank(message = "Tidak Boleh Kosong")
    private String usac_accountno;

    @NotBlank(message = "Tidak Boleh Kosong")
    private String patr_usac_accountNo_to;
   
    @NotNull(message = "Tidak Boleh Kosong")
    private Double nominall;
    
    @NotBlank(message = "Tidak Boleh Kosong")
    private String patr_notes;
    
    @NotNull(message = "Tidak Boleh Kosong")
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    
    @NotBlank(message = "Tidak Boleh Kosong")
    private String patr_invoice_no;
}
