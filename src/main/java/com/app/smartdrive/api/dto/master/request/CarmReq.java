package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarmReq {
    @Size(max = 55, message = "Car Model Name Length Exceeded !")
    @NotNull(message = "Car Model Name Cannot Be Null !")
    private String carmName;

    @NotNull(message = "Car Brand ID Name Cannot Be Null !")
    private Long carmCarbId;
}
