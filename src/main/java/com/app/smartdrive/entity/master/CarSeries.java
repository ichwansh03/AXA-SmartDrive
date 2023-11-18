package com.app.smartdrive.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_series", schema = "mtr")
public class CarSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cars_id")
    private int carsId;

    @Column(name = "cars_name", unique = true)
    private String carsName;

    @Column(name = "cars_passenger")
    private int carsPassenger;

    @Column(name = "cars_carm_id")
    private int carsCarmId;

    @ManyToOne
    @JoinColumn(name = "cars_carm_id")
    private CarModel carModel;
}
