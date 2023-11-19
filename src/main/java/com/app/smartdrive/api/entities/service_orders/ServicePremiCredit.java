package com.app.smartdrive.api.entities.service_orders;

import com.app.smartdrive.api.entities.payment.Payment_transactions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_premi_credit")
public class ServicePremiCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secr_id")
    private Long secrId;

    @Column(name = "secr_serv_id", unique = true)
    private Long secrServId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secr_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secr_patr_trxno", referencedColumnName = "patr_trxno", insertable = false, updatable = false)
    Payment_transactions paymentTransactions;

}
