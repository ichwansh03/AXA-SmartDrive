package com.app.smartdrive.api.dto.master.response;

import com.app.smartdrive.api.dto.master.response.CateRes;
import com.app.smartdrive.api.dto.master.response.IntyRes;
import com.app.smartdrive.api.dto.master.response.ZonesRes;
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
    private Long temiZonesId;
    private String temiIntyName;
    private Long temiCateId;
    private ZonesRes zones;
    private IntyRes insuranceType;
    private CateRes category;
}
