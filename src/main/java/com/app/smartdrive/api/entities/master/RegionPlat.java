package com.app.smartdrive.api.entities.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region_plat", schema = "mtr")
public class RegionPlat {
    @Id
    @Column(name = "regp_name", updatable = false, nullable = false)
    private String regp_name;

    @Column(name = "regp_prov_id")
    private Long regp_prov_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "regp_prov_id", insertable = false, updatable = false)
    private Provinsi provinsi;
}
