package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IntyReq {
    @Size(max = 25, message = "Insurance Type Name Length Exceeded !")
    @NotNull(message = "Insurance Type Name Cannot Be Null !")
    private String intyName;

    @Size(max = 55, message = "Insurance Type Description Length Exceeded !")
    private String intyDesc;
}
