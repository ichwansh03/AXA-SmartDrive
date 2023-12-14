package com.app.smartdrive.api.dto.payment.Request.PaymentTransactions;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTransactionsRequests {
    private String noRekening;
    private String toRekening;
    private String notes;
    @NotNull(message = "Tidak Boleh Kosong")
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment tipePayment;

}
