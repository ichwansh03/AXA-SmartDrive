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

import org.antlr.v4.runtime.misc.DoubleKeyMap;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_accounts", schema = "payment")
public class User_accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usac_id", updatable = false, nullable = false)
    private Long usac_id;

    @Column(name = "usac_accountno", unique = true, length = 30)
    private String usac_accountno;

    @Column(name = "usac_debet")
    private Double usac_debet;

    @Column(name = "usac_credit")
    private Double usac_credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "usac_type", length = 15)
    private EnumClassPayment.EnumPaymentType enumPaymentType;

    // ----------------------------- //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_bank_entityid", referencedColumnName = "bank_entityid")
    @JsonIgnore
    Banks banks;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_fint_entityid", referencedColumnName = "fint_entityid")
    @JsonIgnore
    Fintech fintech;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usac_user_entityid", referencedColumnName = "user_entityid")
    @JsonIgnore
    User user;




    // ManytoOne & OneToMany & ManytoMany // 

    // @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name= "productid", insertable = false, updatable = false)
//     Product product;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "address_id", referencedColumnName = "id")
    //private Address address;

    // @JsonIgnore
    // @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    // private List<OrderDetails> orderDetails;    
// }
}