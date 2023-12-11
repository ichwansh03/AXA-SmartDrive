package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarbReq {
    @Size(max = 55, message = "Car Brand Name Length Exceeded !")
    @NotBlank(message = "Car Brand Name Cannot Be Null !")
    private String cabrName;
}
