package com.app.smartdrive.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_models", schema = "mtr")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carm_id", updatable = false, nullable = false)
    private int carmId;

    @Column(name = "carm_name", unique = true, nullable = false)
    private String carmName;

    @Column(name = "carm_carb_id")
    private String carmCarbId;

    @ManyToOne
    @JoinColumn(name = "carm_carb_id", insertable = false, updatable = false)
    private CarBrand carBrand;

    @OneToMany(mappedBy = "car_models", fetch = FetchType.LAZY)
    private List<CarSeries> carSeries;
}
