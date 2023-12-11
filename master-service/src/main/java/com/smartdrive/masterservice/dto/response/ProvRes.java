package com.smartdrive.masterservice.dto.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvRes {
    private Long provId;

    @Size(max = 85, message = "Province Name Length Exceeded !")
    private String provName;

    private ZonesRes zones;
}
