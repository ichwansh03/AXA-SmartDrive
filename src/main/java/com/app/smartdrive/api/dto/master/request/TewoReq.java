package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TewoReq {
    @Size(max = 55, message = "Work Order Name Length Exceeded !")
    @NotBlank(message = "Work Order Name Cannot Be Null !")
    private String tewoName;

    @NotBlank(message = "Template Service ID Cannot Be Null !")
    private Long tewoTestaId;
}
