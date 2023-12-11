package com.smartdrive.masterservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zones", schema = "mtr")
public class Zones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zones_id", updatable = false, nullable = false)
    private Long zonesId;

    @Column(name = "zones_name")
    private String zonesName;

    @OneToMany(mappedBy = "zones", fetch = FetchType.LAZY)
    private List<TemplateInsurancePremi> templateInsurancePremis;

    @OneToMany(mappedBy = "zones", fetch = FetchType.LAZY)
    private List<Provinsi> provinsi;
}
