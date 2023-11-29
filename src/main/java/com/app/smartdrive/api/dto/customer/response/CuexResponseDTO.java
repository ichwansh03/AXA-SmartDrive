package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuexResponseDTO {
    private Long cuexId;

    private Long cuexCreqEntityid;

    private String cuexName;

    private int cuexTotalItem;

    private double cuex_nominal;
}
