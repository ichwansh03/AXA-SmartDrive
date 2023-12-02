package com.app.smartdrive.api.entities.payment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_transactions", schema  = "payment")
public class PaymentTransactions {

    @Id
    @Column(name="patr_trxno", length = 55)
    private String patrTrxno;

    @CreatedDate
    @Column(name = "patr_created_on")
    private LocalDateTime patr_created_on;

    @Column(name = "patr_debet")
    private Double patr_debet;

    @Column(name = "patr_credit")
    private Double patr_credit;

    @Column(name = "patr_usac_accountno_from")
    private Long patr_usac_accountNo_from;

    @Column(name = "patr_usac_accountno_to")
    private Long patr_usac_accountNo_to;

    @Enumerated(EnumType.STRING)
    @Column(name = "patr_type", length = 15)
    private EnumClassPayment.EnumPayment enumPayment;

    @Column(name = "patr_invoice_no", length = 55)
    private String patr_invoice_no;

    @Column(name = "patr_notes", length = 125)
    private String patr_notes;

    
    @Column(name="patr_trxno_rev")
    private String patrTrxnoRev;

 
    // @OneToMany(mappedBy = "paymentTransactions", cascade = CascadeType.ALL, orphanRemoval = true)
    // List<ServicePremiCredit> servicePremiCredits;

    @ManyToOne
    @JoinColumn(name = "patr_trxno_rev", referencedColumnName = "patr_trxno" ,insertable = false, updatable = false)
    @JsonIgnore
    PaymentTransactions referencedTransaction;

    @OneToMany(mappedBy = "referencedTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentTransactions> referencingTransactions;
    
}