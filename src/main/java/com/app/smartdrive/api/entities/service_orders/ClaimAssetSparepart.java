package com.app.smartdrive.api.entities.service_orders;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claim_asset_sparepart", schema = "so")
public class ClaimAssetSparepart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "casp_id")
    private Long caspId;

    @Column(name = "casp_item_name")
    @Size(max = 55)
    private String caspItemName;

    @Column(name = "casp_quantity")
    private Integer caspQuantity;

    @Column(name = "casp_item_price")
    private Double caspItemPrice;

    @Column(name = "casp_subtotal")
    private Double caspSubtotal;

    @Column(name = "casp_part_entityid")
    private Long caspPartEntityid;

    @Column(name = "casp_sero_id")
    private String caspSeroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casp_part_entityid", referencedColumnName = "part_entityid", insertable = false, updatable = false)
    Partners partners;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casp_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders caspServiceOrders;
}
