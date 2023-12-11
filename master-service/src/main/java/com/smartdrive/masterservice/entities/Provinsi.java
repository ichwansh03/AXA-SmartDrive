package com.smartdrive.masterservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long provId;

    @Column(name = "prov_name", unique = true)
    private String provName;

    @Column(name = "prov_zones_id")
    private Long provZonesId;

    @OneToMany(mappedBy = "provinsi")
    private List<Cities> cities;

    @OneToMany(mappedBy = "provinsi")
    private List<RegionPlat> regionPlats;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "prov_zones_id", insertable = false,updatable = false)
    private Zones zones;
}
