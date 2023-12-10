package com.app.smartdrive.api.dto.master.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationReq {
    private LocalDate ibmeDate;

    @NotNull(message = "Inbox Message Source ID Cannot Be Null")
    private int ibme_entityid_source;

    @NotNull(message = "Inbox Message Target ID Cannot Be Null")
    private int ibme_entityid_target;

    @NotNull(message = "Inbox Message Type Cannot Be Null")
    private int ibme_type;

    private int ibme_count = 0;

    @NotNull(message = "Target Mail Cannot Be Null")
    private String targetMail;

    @NotNull(message = "Subject Mail Cannot Be Null")
    private String subjectMail;

    @NotNull(message = "Body Mail Cannot Be Null")
    private String bodyMail;
}
