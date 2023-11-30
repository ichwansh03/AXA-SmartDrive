package com.app.smartdrive.api.dto.master;

import com.app.smartdrive.api.entities.master.Enum.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateTypeDto {
    private Long tetyId;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.Tety tetyName;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.TetyGroup tetyGroup;

    private List<TemplateServiceTaskDto> templateServiceTaskDto;
}
