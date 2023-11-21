// CarModel
package com.app.smartdrive.api.entities.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_models", schema = "mtr")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carm_id", updatable = false, nullable = false)
    private Long carmId;

    @Column(name = "carm_name", unique = true, nullable = false)
    private String carmName;

    @Column(name = "carm_cabr_id", nullable = false)
    private Long carmCarbId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "carm_cabr_id", insertable = false, updatable = false)
    private CarBrand carBrand;

    @OneToMany(mappedBy = "carModel", fetch = FetchType.LAZY)
    private List<CarSeries> carSeries;
}
