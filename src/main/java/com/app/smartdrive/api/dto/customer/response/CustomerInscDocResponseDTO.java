package com.app.smartdrive.api.dto.customer.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInscDocResponseDTO {
    private Long cadocId;

    private Long cadocCreqEntityid;

    private String cadocFilename;

    private String cadocFiletype;

    private int cadocFilesize;

    private EnumCustomer.CadocCategory cadocCategory;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cadocModifiedDate;
}
