package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "batch_partner_invoice", schema = "partners")
@EntityListeners({AuditingEntityListener.class})
@AllArgsConstructor
@NoArgsConstructor
public class BatchPartnerInvoice {
    @Id
    @Column(name = "bpin_invoiceno")
    private String no;

    @CreatedDate
    @Column(name = "bpin_created_on")
    private LocalDateTime createdOn;

    @Column(name = "bpin_subtotal")
    private Double subTotal;
    @Column(name = "bpin_tax")
    private Double tax;

    @Column(name = "bpin_accountno")
    private String accountNo;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(name = "bpin_status")
    private BpinStatus status = BpinStatus.NOT_PAID;

    @Column(name = "bpin_paid_date")
    private LocalDateTime paidDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bpin_patrn_entityid")
    private Partner partner;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "bpin_patr_trxno")
    private PaymentTransactions paymentTransactions;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "bpin_sero_id")
    private ServiceOrders serviceOrders;

}
