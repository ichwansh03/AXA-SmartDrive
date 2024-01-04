package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArwgReq {
    @Size(max = 55, message = "Area Work Group Description Length Exceeded !")
    private String arwgDesc;

    @NotNull(message = "Area Work Group City ID Cannot Be Null !")
    private Long arwgCityId;
}
