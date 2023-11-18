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
@Table(name = "provinsi", schema = "mtr")
public class Provinsi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id", updatable = false, nullable = false)
    private int provId;

    @Column(name = "prov_name", unique = true)
    private String provName;

    @Column(name = "prov_zones_id", insertable = false, nullable = false)
    private int prov_zones_id;

    @OneToMany(mappedBy = "provinsi")
    private List<Cities> cities;

    @OneToMany(mappedBy = "provinsi")
    private List<RegionPlat> regionPlats;

    @ManyToOne
    @JoinColumn(name = "prov_zones_id")
    private Zones zones;
}