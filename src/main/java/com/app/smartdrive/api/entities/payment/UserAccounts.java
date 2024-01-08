package com.app.smartdrive.api.entities.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Builder;
import org.antlr.v4.runtime.misc.DoubleKeyMap;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_accounts", schema = "payment")
public class UserAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usac_id", updatable = false, nullable = false)
    private Long usacId;

    @Column(name = "usac_accountno", unique = true, length = 30)
    private String usacAccountno;

    @Column(name = "usac_debet")
    private BigDecimal usacDebet;

    @Column(name = "usac_credit")
    private Double usacCredit;

    @Enumerated(EnumType.STRING)
    @Column(name = "usac_type", length = 15)
    private EnumClassPayment.EnumPaymentType enumPaymentType;

    @Column(name = "usac_bank_entityid")
    private Long usacBankEntityid;

    @Column(name = "usac_fint_entityid")
    private Long usacFintEntityid;

    @Column(name = "usac_user_entityid")
    private Long usacUserEntityid;

    // ----------------------------- //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_bank_entityid",referencedColumnName = "bank_entityid",insertable = false, updatable = false)
    @JsonIgnore
    Banks banks;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_fint_entityid", referencedColumnName = "fint_entityid", insertable = false, updatable = false)
    @JsonIgnore
    Fintech fintech;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_user_entityid", referencedColumnName = "user_entityid", insertable = false, updatable = false)
    @JsonIgnore
    User user;

}