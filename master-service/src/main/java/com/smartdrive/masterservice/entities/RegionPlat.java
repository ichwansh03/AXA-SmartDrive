package com.smartdrive.masterservice.entities;

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
    private String regpName;

    @Column(name = "regp_desc")
    private String regpDesc;

    @Column(name = "regp_prov_id")
    private Long regpProvId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "regp_prov_id", insertable = false, updatable = false)
    private Provinsi provinsi;
}
