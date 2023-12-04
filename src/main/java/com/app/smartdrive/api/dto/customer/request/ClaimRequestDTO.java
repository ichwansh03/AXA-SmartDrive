package com.app.smartdrive.api.dto.customer.request;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestDTO {

    private Long creqEntityId;

    private String cuclCreateDate;

    private Double cuclEventPrice;

    private Double cuclSubtotal;

    private String cuclReason;
}
