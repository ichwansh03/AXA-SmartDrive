package com.app.smartdrive.entities.payment;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "banks", schema = "payment")
public class Banks {
    
    @Id
    @Column(name = "bank_entityid")
    private Long bank_entityid;

    @Column(name = "bank_name",unique = true ,length = 5)
    private String bank_name;

    @Column(name = "bank_desc", length = 55)
    private String bank_desc;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_entityid", referencedColumnName = "entityid")
    Business_entity business_entity;

    
    @OneToMany(mappedBy = "banks", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<User_accounts> user_accounts;   

    
}
