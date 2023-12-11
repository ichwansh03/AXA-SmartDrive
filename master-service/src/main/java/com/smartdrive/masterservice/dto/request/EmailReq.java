package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailReq {

    @NotNull(message = "Source Email Cannot Be Null !")
    private String to;

    @NotNull(message = "Subject Email Cannot Be Null !")
    private String subject;

    @NotNull(message = "Body Cannot Be Null !")
    private String body;

    private String cc;

    private String bcc;
}
