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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim_asset_evidence", schema = "so")
public class ClaimAssetEvidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caev_id")
    private Long caevId;

    //multipart
    @Column(name = "caev_filename")
    @Size(max = 55)
    private String caevFileName;

    @Column(name = "caev_filesize")
    private Long caevFileSize;

    @Column(name = "caev_filetype")
    @Size(max = 15)
    private String caevFileType;

    @Column(name = "caev_url")
    private String caevUrl;

    @Column(name = "caev_note")
    private String caevNote;

    @Column(name = "caev_part_entityid")
    private Long caevPartEntityid;

    @Column(name = "caev_sero_id")
    @Size(max = 25)
    private String caevSeroId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caev_part_entityid", referencedColumnName = "part_entityid", insertable = false, updatable = false)
    Partner caevPartners;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caev_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders caevServiceOrders;
}
