package com.app.smartdrive.api.entities.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "payment_transactions", schema  = "payment")
public class PaymentTransactions {
   
    @Id
    @Column(name="patr_trxno", length = 55,nullable = false)
    private String patrTrxno;



    @CreatedDate
    @Column(name = "patr_created_on")
    private LocalDateTime patrCreatedOn;

    @Column(name = "patr_debet")
    private BigDecimal patrDebet;

    @Column(name = "patr_credit")
    private BigDecimal patrCredit;

    @Column(name = "patr_usac_accountno_from")
    private String patrUsacAccountNoFrom;

    @Column(name = "patr_usac_accountno_to")
    private String patrUsacAccountNoTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "patr_type", length = 15)
    private EnumClassPayment.EnumPayment patrType;

    @Column(name = "patr_invoice_no", length = 55)
    private String patrInvoiceNo;

    @Column(name = "patr_notes", length = 125)
    private String patrNotes;

    
    @Column(name="patr_trxno_rev")
    private String patrTrxnoRev;

 


    @OneToMany(mappedBy = "paymentTransactions")
    private List<ServicePremiCredit> servicePremiCredits;

    @OneToOne
    @JoinColumn(name = "patr_trxno_rev", referencedColumnName = "patr_trxno" ,insertable = false, updatable = false)
    @JsonIgnore
    PaymentTransactions referencedTransaction;

    @OneToOne(mappedBy = "referencedTransaction", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private PaymentTransactions referencingTransaction;

    
    
}