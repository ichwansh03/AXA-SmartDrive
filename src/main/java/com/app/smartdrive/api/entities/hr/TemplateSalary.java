package com.app.smartdrive.api.entities.hr;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="template_salary",schema="hr")
public class TemplateSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tesal_id")
    private Long tesalId;

    @Column(name="tesal_name",unique = true)
    private String tesalName;

    @Column(name="tesal_nominal")
    private Double tesalNominal;

    @Column(name="tesal_rate_min")
    private Double tesalRateMin;

    @Column(name="tesal_rate_max")
    private Double tesalRateMax;



}
