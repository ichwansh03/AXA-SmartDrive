package com.app.smartdrive.api.entities.payment;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.engine.jdbc.Size;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "fintech", schema = "payment")
public class Fintech {
    
    @Id
    @Column(name = "fint_entityid")
    private Long fintEntityid;

    @Column(name = "fint_name", unique = true, length = 5)
    private String fintName;

    @Column(name = "fint_desc", length = 55)
    private String fintDesc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fint_entityid", referencedColumnName = "entityid")
    @JsonBackReference
    BusinessEntity businessEntity;

    // @JsonIgnore
    @OneToMany(mappedBy = "fintech", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserAccounts> userAccounts;

    public Optional<Fintech> stream() {
        return null;
    }   


}
