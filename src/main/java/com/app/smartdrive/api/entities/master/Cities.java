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
@Table(name = "cities", schema = "mtr")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", updatable = false, nullable = false)
    private int cityId;

    @Column(name = "city_name", unique = true)
    private String cityName;

    @Column(name = "city_prov_id")
    private int cityProvId;

    @OneToMany(mappedBy = "cities", fetch = FetchType.LAZY)
    private List<AreaWorkGroup> areaWorkGroups;

    @ManyToOne
    @JoinColumn(name = "city_prov_id", insertable = false, updatable = false)
    private Provinsi provinsi;
}
