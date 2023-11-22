package com.app.smartdrive.api.entities.master;

import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private Long carsId;

    @Column(name = "cars_name", unique = true)
    private String carsName;

    @Column(name = "cars_passenger")
    private int carsPassenger;

    @Column(name = "cars_carm_id", nullable = false)
    private Long carsCarmId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cars_carm_id", insertable = false, updatable = false)
    private CarModel carModel;

    @JsonManagedReference
    @OneToMany(mappedBy = "carSeries", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerInscAssets> customerInscAssets;
}
