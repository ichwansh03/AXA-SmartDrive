package com.smartdrive.masterservice.entities;

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
    private Long cabrID;

    @Column(name = "cabr_name")
    private String cabrName;

    @OneToMany(mappedBy = "carBrand", fetch = FetchType.LAZY)
    private List<CarModel> carModels;
}
