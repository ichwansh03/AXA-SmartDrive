package com.app.smartdrive.api.dto.service_order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ClaimAssetRequestDto {

    @NotNull
    private Long partnerId;

    @NotNull
    private String serviceOrderId;

    @NotNull
    @Valid
    private List<ClaimAssetRequest> claimAssets;
}
