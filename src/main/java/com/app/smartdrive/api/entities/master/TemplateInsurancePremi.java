package com.app.smartdrive.api.entities.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "template_insurance_premi", schema = "mtr")
public class TemplateInsurancePremi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temi_id", updatable = false, nullable = false)
    private int temiId;

    @Column(name = "temi_name")
    private String temiName;

    @Column(name = "temi_rate_min")
    private Double temiRateMin;

    @Column(name = "temi_rate_max")
    private Double temiRateMax;

    @Column(name = "temi_nominal")
    private Double temiNominal;

    @Column(name = "temi_type", columnDefinition = "CHECK (temi_type IN ('Category', 'Extend'))")
    private String temiType;

    @Column(name = "temi_zones_id", insertable = false, nullable = false)
    private int temiZonesId;

    @Column(name = "temi_inty_name", insertable = false, nullable = false)
    private String temiIntyId;

    @Column(name = "temi_cate_id", insertable = false, nullable = false)
    private int temiCateId;

    @ManyToOne
    @JoinColumn(name = "temi_zones_id")
    private Zones zones;

    @ManyToOne
    @JoinColumn(name = "temi_inty_name")
    private InsuranceType insuranceType;

    @ManyToOne
    @JoinColumn(name = "temi_cate_id")
    private Category category;
}
