package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerInscDocId.class)
@Table(name = "customer_insc_doc", schema = "customer")
@Entity
public class CustomerInscDoc {
    @Id
    @Column(name = "cadoc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cadocId;

    @Id
    @Column(name = "cadoc_creq_entityid")
    private Long cadocCreqEntityid;

    @JsonBackReference
    @OneToOne
    @MapsId("cadocCreqEntityid")
    @JoinColumn(name = "cadoc_creq_entityid")
    private CustomerInscAssets customerInscAssets;

    @Column(name = "cadoc_filename", length = 15)
    private String cadocFilename;

    @Column(name = "cadoc_filetype", length = 15)
    private String cadocFiletype;

    @Column(name = "cadoc_filesize")
    private int cadocFilesize;

    @Enumerated(EnumType.STRING)
    @Column(name = "cadoc_category", length = 15)
    private EnumCustomer.CadocCategory cadocCategory;

    @Column(name = "cadoc_modified_date")
    private LocalDateTime cadocModifiedDate;






}
