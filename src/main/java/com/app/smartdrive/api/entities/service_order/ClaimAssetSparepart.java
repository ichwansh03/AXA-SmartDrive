package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.partner.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casp_part_entityid", referencedColumnName = "part_entityid", insertable = false, updatable = false)
    Partner caspPartners;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casp_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders caspServiceOrders;
}
