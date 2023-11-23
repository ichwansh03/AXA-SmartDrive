package com.app.smartdrive.api.entities.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerInscExtendId.class)
@Data
@Table(name = "customer_insc_extend", schema = "customer")
@Entity
public class CustomerInscExtend {

    @Id
    @Column(name = "cuex_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, 
      generator = "id-generator")
    @SequenceGenerator(
      name = "id-generator",
      sequenceName = "cadoc_cuex_id",
      allocationSize = 1
    )
    private Long cuexId;

    @Id
    @Column(name = "cuex_creq_entityid")
    private Long cuexCreqEntityid;

    @JsonBackReference
    @ManyToOne
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
