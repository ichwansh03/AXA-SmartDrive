package com.app.smartdrive.api.dto.master.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarbRes {
    private Long cabrID;

    @Size(max = 55, message = "Car Brand Name Length Exceeded !")
    private String cabrName;
}

