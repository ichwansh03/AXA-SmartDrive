package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ServicePremiCreditId.class)
@Entity
@Table(schema = "so")
@DynamicInsert
@DynamicUpdate
public class ServicePremiCredit {

    //CREATE SEQUENCE serc_seq START WITH 1 INCREMENT BY 1;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idsecr-generator")
    @SequenceGenerator(name = "idsecr-generator", sequenceName = "serc_seq", allocationSize = 1)
    private Long secrId;

    @Id
    @Column(name = "secr_serv_id")
    private Long secrServId;

    private String secrYear;

    private BigDecimal secrPremiDebet;

    private BigDecimal secrPremiCredit;

    @LastModifiedDate
    private LocalDateTime secrTrxDate;

    private LocalDateTime secrDuedate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secr_serv_id", insertable = false, updatable = false)
    private Services services;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secr_patr_trxno")
    PaymentTransactions paymentTransactions;

}
