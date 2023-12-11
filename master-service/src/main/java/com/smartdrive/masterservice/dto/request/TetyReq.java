package com.smartdrive.masterservice.dto.request;

import com.smartdrive.masterservice.entities.Enum.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TetyReq {
    @NotNull(message = "Tety Name Cannot Be Null !")
    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.Tety tetyName;

    @NotNull(message = "Tety Group Cannot Be Null !")
    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.TetyGroup tetyGroup;
}
