package com.smartdrive.serviceorderservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClaimAssetRequestSparePartDto {

    @NotBlank
    private String serviceOrderId;
    @NotNull
    private long partnerId;
    @Valid
    @NotNull
    private List<ClaimAssetRequestSparePart> claimAssets;
}
