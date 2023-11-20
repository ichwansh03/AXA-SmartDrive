package com.app.smartdrive.api.dto.master;

import com.app.smartdrive.api.entities.master.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemplateTypeDto {
    private Long tetyId;

    @Size(max = 25, message = "Template Type Name Length Exceeded !")
    @NotBlank(message = "Template Type Name Cannot Be Null !")
    private EnumModuleMaster tetyName;

    @Size(max = 15, message = "Template Group Length Exceeded !")
    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.TetyGroup tetyGroup;
}
