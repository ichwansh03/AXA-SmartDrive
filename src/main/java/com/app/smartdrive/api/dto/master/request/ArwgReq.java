package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArwgReq {
    @Size(max = 15, message = "Area Work Group Code Length Exceeded !")
    @NotNull(message = "Area Work Group Code Cannot Be Null !")
    private String arwgCode;

    @Size(max = 55, message = "Area Work Group Description Length Exceeded !")
    private String arwgDesc;

    @NotNull(message = "Area Work Group City ID Cannot Be Null !")
    private Long arwgCityId;
}
