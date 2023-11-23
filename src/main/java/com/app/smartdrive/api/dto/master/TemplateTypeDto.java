package com.app.smartdrive.api.dto.master;

import com.app.smartdrive.api.entities.master.Enum.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class TemplateTypeDto {
    private Long tetyId;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.Tety tetyName;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.TetyGroup tetyGroup;
}
