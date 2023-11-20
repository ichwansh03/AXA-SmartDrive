package com.app.smartdrive.api.dto.master;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InboxMessagingDto {
    private Long ibmeName;

    private LocalDate ibmeDate;

    private int ibme_entityid_source;

    private int ibme_target_target;

    private int ibme_type;

    private int ibme_count;
}
