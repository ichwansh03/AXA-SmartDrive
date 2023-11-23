package com.app.smartdrive.api.dto.master;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class IbmeDto {
    private Long ibmeId;

    private LocalDate ibmeDate;

    private int ibme_entityid_source;

    private int ibme_entityid_target;

    private int ibme_type;

    private int ibme_count;
}
