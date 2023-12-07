package com.app.smartdrive.api.dto.user.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BussinessEntityResponseDTO {
    private Long entityId;

    private LocalDateTime entityModifiedDate;
}
