package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ServicePremiCredit.class)
@Entity
@Table(name = "service_premi_credit", schema = "so")
@DynamicInsert
@DynamicUpdate
public class ServicePremiCredit {

    //CREATE SEQUENCE serc_seq START WITH 1 INCREMENT BY 1;
    @Id
    @Column(name = "secr_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idsecr-generator")
    @SequenceGenerator(name = "idsecr-generator", sequenceName = "serc_seq", allocationSize = 1)
    private Long secrId;

    @Id
    @Column(name = "secr_serv_id")
    private Long secrServId;

    @Column(name = "secr_year")
    @Size(max = 4)
    private String secrYear;

    @Column(name = "secr_premi_debet")
    private BigDecimal secrPremiDebet;

    @Column(name = "secr_premi_credit")
    private BigDecimal secrPremiCredit;

    @Column(name = "secr_trx_date")
    private LocalDateTime secrTrxDate;

    @Column(name = "secr_duedate")
    private LocalDateTime secrDuedate;

    @Column(name = "secr_patr_trxno")
    @Size(max = 55)
    private String secrPatrTrxno;

    @ManyToOne
    @JoinColumn(name = "secr_serv_id", referencedColumnName = "semi_serv_id", insertable = false, updatable = false)
    ServicePremi servicePremi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secr_patr_trxno", referencedColumnName = "patr_trxno", insertable = false, updatable = false)
    PaymentTransactions paymentTransactions;
}
