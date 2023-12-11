package com.smartdrive.masterservice.dto.response;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemiRes {
    private Long temiId;

    @Size(max = 256, message = "Template Insurance Name Length Exceeded !")
    private String temiName;

    private Double temiRateMin;
    private Double temiRateMax;
    private Double temiNominal;
    private String temiType;
    private ZonesRes zones;
    private IntyRes insuranceType;
    private CateRes category;
}
