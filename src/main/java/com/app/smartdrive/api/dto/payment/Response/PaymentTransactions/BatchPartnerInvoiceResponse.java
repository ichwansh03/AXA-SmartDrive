package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchPartnerInvoiceResponse {
    private String no;
    private LocalDateTime createdOn;
    private Double subTotal;
    private Double tax;
    private String accountNo;
    @Enumerated(EnumType.STRING)
    private BpinStatus status;
    private LocalDateTime paidDate;
    private Partner partner;
    private PaymentTransactions paymentTransactions;
    private ServiceOrders service_orders;

}
