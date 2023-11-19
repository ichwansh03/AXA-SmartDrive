package com.app.smartdrive.api.entities.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerInscExtendId.class)
@Data
@Table(name = "customer_insc_extend", schema = "customer")
@Entity
public class CustomerInscExtend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuex_id")
    private Long cuexId;

    @Id
    @Column(name = "cuex_creq_entityid")
    private Long cuexCreqEntityid;

    @OneToOne
    @MapsId("cuexCreqEntityid")
    @JoinColumn(name = "cuex_creq_entityid")
    private CustomerInscAssets customerInscAssets;

    @Column(name = "cuex_name", length = 256)
    private String cuexName;

    @Column(name = "cuex_total_item")
    private int cuexTotalItem;

    @Column(name = "cuex_nominal")
    private double cuex_nominal;


}
