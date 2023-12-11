package com.smartdrive.masterservice.dto.response;

import com.smartdrive.masterservice.entities.Enum.EnumModuleMaster;
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
