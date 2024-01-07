package com.app.smartdrive.api.dto.payment.Response.HistoryTransactions;

import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaidPartnerHistoryDtoResponse {
    private String Nomor_Invoice;
    private LocalDateTime CreatedOn;
    private Double Total;
    private Double Tax;
    private String Nomor_Rekening;
    @Enumerated(EnumType.STRING)
    private BpinStatus Status;
    private LocalDateTime Tanggal_Pembayaran;
    private Partner Partner_entityid;
    private PaymentTransactions Nomor_Transaksi;
    private ServiceOrders Id_ServiceOrders;
}
