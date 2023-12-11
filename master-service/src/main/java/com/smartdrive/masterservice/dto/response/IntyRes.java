package com.smartdrive.masterservice.dto.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntyRes {
    @Size(max = 25, message = "Region Plat Name Length Exceeded !")
    private String intyName;

    @Size(max = 55, message = "Insurance Type Description Length Exceeded !")
    private String intyDesc;
}
