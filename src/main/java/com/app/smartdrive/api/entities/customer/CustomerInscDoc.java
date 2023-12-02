package com.app.smartdrive.api.entities.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerInscDocId.class)
@Table(name = "customer_insc_doc", schema = "customer")
@Entity
public class CustomerInscDoc {

    @Id
    @Column(name = "cadoc_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, 
      generator = "id-generator")
    @SequenceGenerator(
      name = "id-generator",
      sequenceName = "cadoc_cuex_id",
      allocationSize = 1
    )
    private Long cadocId;

    @Id
    @Column(name = "cadoc_creq_entityid")
    private Long cadocCreqEntityid;

    @JsonIgnore
    @ManyToOne
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
