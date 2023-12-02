package com.app.smartdrive.api.entities.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cities", schema = "mtr")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", updatable = false, nullable = false)
    private Long cityId;

    @Column(name = "city_name", unique = true)
    private String cityName;

    @Column(name = "city_prov_id")
    private Long cityProvId;


    @OneToMany(mappedBy = "cities", fetch = FetchType.LAZY)
    private List<AreaWorkGroup> areaWorkGroups;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_prov_id", insertable = false, updatable = false)
    private Provinsi provinsi;

    @OneToMany(mappedBy = "city")
    @JsonManagedReference
    private List<UserAddress> userAddresses;

    @JsonManagedReference
    @OneToMany(mappedBy = "city")
    private List<CustomerInscAssets> customerInscAssets;
}
