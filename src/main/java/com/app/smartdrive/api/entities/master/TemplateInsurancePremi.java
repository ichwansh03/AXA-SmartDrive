package com.app.smartdrive.api.entities.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long temiId;

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

    @Column(name = "temi_zones_id", nullable = false)
    private Long temiZonesId;

    @Column(name = "temi_inty_name", nullable = false)
    private String temiIntyName;

    @Column(name = "temi_cate_id", nullable = false)
    private Long temiCateId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "temi_zones_id", insertable = false, updatable = false)
    private Zones zones;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "temi_inty_name", insertable = false, updatable = false)
    private InsuranceType insuranceType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "temi_cate_id", insertable = false, updatable = false)
    private Category category;
}
