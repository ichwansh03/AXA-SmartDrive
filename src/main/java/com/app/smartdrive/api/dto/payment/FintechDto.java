package com.app.smartdrive.api.dto.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FintechDto {
  
    private String fint_name;
    private String fint_desc;
}
