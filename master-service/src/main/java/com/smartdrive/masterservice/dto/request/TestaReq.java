package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TestaReq {
    @Size(max = 55, message = "Service Task Name Length Exceeded !")
    @NotNull(message = "Service Task Name Cannot Be Null !")
    private String testaName;

    private Integer testaTetyId;

    @Size(max = 50, message = "Service Task Group Length Exceeded !")
    private String testaGroup;

    @Size(max = 100, message = "Service Task Call Method Length Exceeded !")
    private String testaCallMethod;

    private Integer testaSeqOrder;
}
