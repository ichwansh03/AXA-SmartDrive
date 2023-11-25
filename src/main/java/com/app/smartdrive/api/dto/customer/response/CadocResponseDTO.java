package com.app.smartdrive.api.dto.customer.response;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CadocResponseDTO {
    
    private Long cadocCreqEntityid;

    private String cadocFilename;

    private String cadocFiletype;

    private int cadocFilesize;

    private EnumCustomer.CadocCategory cadocCategory;

    private LocalDateTime cadocModifiedDate;
}
