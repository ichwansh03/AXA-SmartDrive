package com.app.smartdrive.api.entities.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_brands", schema = "mtr")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabr_id", updatable = false, nullable = false)
    private int cabrID;

    @Column(name = "cabr_name")
    private String cabrName;

    @OneToMany(mappedBy = "car_brands", fetch = FetchType.LAZY)
    private List<CarModel> carModels;
}
