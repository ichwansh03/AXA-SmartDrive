package com.app.smartdrive.api.dto.service_order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimAssetRequestSparePart {

    @NotBlank
    private String item;
    @Positive
    private int Qty;
    @Positive
    private double price;
    @Positive
    private double totalPrice;

}
