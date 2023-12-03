package com.app.smartdrive.api.dto.master.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IbmeRes {
    private Long ibmeId;
    private LocalDate ibmeDate;
    private int ibme_entityid_source;
    private int ibme_entityid_target;
    private int ibme_type;
    private int ibme_count;
}
