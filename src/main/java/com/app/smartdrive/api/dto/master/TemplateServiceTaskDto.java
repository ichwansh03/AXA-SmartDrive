package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateServiceTaskDto {
    private Long testaId;

    @Size(max = 55, message = "Template Service Task Length Exceeded !")
    private String testaName;

    private Long testaTetyId;
    private String testaGroup;
    private String testaCallMethod;
    private Integer testaSeqOrder;
    private List<TewoDto> tewoDto;
}
