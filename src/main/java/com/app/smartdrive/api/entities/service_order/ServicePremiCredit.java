package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_premi_credit", schema = "so")
public class ServicePremiCredit {

    @EmbeddedId
    ServicePremiCreditId creditId;

    @Column(name = "secr_year")
    @Size(max = 4)
    private String secrYear;

    @Column(name = "secr_premi_debet")
    private Double secrPremiDebet;

    @Column(name = "secr_premi_credit")
    private Double secrPremiCredit;

    @Column(name = "secr_trx_date")
    private LocalDate secrTrxDate;

    @Column(name = "secr_duedate")
    private LocalDate secrDuedate;

    @Column(name = "secr_patr_trxno")
    @Size(max = 55)
    private String secrPatrTrxno;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secr_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services services;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secr_patr_trxno", referencedColumnName = "patr_trxno", insertable = false, updatable = false)
    PaymentTransactions paymentTransactions;
}
