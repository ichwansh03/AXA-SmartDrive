package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TewoDto {
    private Long tewoId;

    @Size(max = 55, message = "Task Work Order Length Exceeded !")
    private String tewoName;

    private Long tewoTestaId;
}
