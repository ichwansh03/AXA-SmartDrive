package com.app.smartdrive.api.entities.master;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "insurance_type", schema = "mtr")
public class InsuranceType {
    @Id
    @Column(name = "inty_name", updatable = false, nullable = false)
    private String intyName;

    @Column(name = "inty_desc")
    private String intyDesc;

    @OneToMany(mappedBy = "insuranceType", fetch = FetchType.LAZY)
    private List<TemplateInsurancePremi> templateInsurancePremis;

    @JsonManagedReference
    @OneToMany(mappedBy = "insuranceType", cascade = CascadeType.ALL)
    private List<CustomerInscAssets> customerInscAssets;
}
