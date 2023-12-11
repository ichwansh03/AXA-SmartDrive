package com.app.smartdrive.api.dto.service_order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ClaimAssetRequest {

    @NotNull
    private MultipartFile file;
    @NotBlank
    private String note;
    @NotNull
    private Double serviceFee;
}
