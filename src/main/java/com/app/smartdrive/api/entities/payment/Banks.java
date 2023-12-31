package com.app.smartdrive.api.entities.payment;

import java.util.List;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "banks", schema = "payment")
public class Banks {
    
    @Id
    @Column(name = "bank_entityid")
    private Long bankEntityid;

    @Column(name = "bank_name",unique = true ,length = 5)
    private String bankName;

    @Column(name = "bank_desc", length = 55)
    private String bankDesc;


    @OneToOne
    @MapsId
    @JoinColumn(name = "bank_entityid")
    @JsonBackReference
    BusinessEntity businessEntity;

    // @JsonIgnore
    @OneToMany(mappedBy = "banks", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserAccounts> userAccounts;

    
}
