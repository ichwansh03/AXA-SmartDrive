package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuexResponseDTO {
    private Long cuexId;

    private Long cuexCreqEntityid;

    private String cuexName;

    private int cuexTotalItem;

    private double cuex_nominal;
}
