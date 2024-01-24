package com.app.smartdrive.api.dto.master.response;

import lombok.Data;

@Data
public class TewoRes {
    private Long tewoId;
    private String tewoName;
    private Long tewoTestaId;
    private TestaRes templateServiceTask;
}
