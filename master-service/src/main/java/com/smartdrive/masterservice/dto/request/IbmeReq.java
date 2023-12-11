package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IbmeReq {
    private LocalDate ibmeDate;

    @NotNull(message = "Inbox Message Source ID Cannot Be Null")
    private int ibme_entityid_source;

    @NotNull(message = "Inbox Message Target ID Cannot Be Null")
    private int ibme_entityid_target;

    @NotNull(message = "Inbox Message Type Cannot Be Null")
    private int ibme_type;

    private int ibme_count = 0;
}
