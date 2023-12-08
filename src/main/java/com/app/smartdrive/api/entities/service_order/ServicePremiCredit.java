package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ServicePremiCreditId.class)
@Entity
@Table(name = "service_premi_credit", schema = "so")
@DynamicInsert
@DynamicUpdate
public class ServicePremiCredit {

    //CREATE SEQUENCE serc_seq START WITH 1 INCREMENT BY 1;
    @Id
    @Column(name = "secr_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idsecr-generator")
    @SequenceGenerator(name = "idsecr-generator", sequenceName = "serc_seq", allocationSize = 1)
    private Long secrId;

    @Id
    @Column(name = "secr_serv_id", insertable = false, updatable = false)
    private Long secrServId;

//    @EmbeddedId
//    ServicePremiCreditId servicePremiCreditId;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secr_serv_id")
    private Services services;

//    @JsonIgnore
//    @OneToMany(mappedBy = "")
//    PaymentTransactions paymentTransactions;

}
