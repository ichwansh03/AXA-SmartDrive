package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AreaWorkGroupDto {
    private String arwgCode;

    @Size(max = 55, message = "Area Work Group Description Length Exceeded !")
    private String arwgDesc;

    private int arwgCityId;
}
