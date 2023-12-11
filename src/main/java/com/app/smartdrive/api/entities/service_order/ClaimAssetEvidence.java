package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.partner.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim_asset_evidence", schema = "so")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "caev_service_fee")
    private Double caevServiceFee;

    @CreatedDate
    @Column(name = "caev_created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caev_part_entityid")
    Partner caevPartners;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caev_sero_id")
    ServiceOrders caevServiceOrders;
}
