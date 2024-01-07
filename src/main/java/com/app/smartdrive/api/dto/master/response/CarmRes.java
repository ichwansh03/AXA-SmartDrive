package com.app.smartdrive.api.dto.master.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarmRes {
    private Long carmId;

    @Size(max = 55, message = "Car Model Name Length Exceeded !")
    private String carmName;

    private Long carmCarbId;

    private CarbRes carBrand;
}
