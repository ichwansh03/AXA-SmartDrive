package com.app.smartdrive.api.dto.master.response;

import com.app.smartdrive.api.entities.master.Enum.EnumModuleMaster;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TetyRes {
    private Long tetyId;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.Tety tetyName;

    @Enumerated(EnumType.STRING)
    private EnumModuleMaster.TetyGroup tetyGroup;
}
