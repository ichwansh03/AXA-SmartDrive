package com.app.smartdrive.api.entities.master;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "insurance_type", fetch = FetchType.LAZY)
    private List<TemplateInsurancePremi> templateInsurancePremis;
}
