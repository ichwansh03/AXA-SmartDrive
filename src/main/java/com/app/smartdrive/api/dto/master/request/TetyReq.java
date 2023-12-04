package com.app.smartdrive.api.dto.master.request;

import com.app.smartdrive.api.entities.master.Enum.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
