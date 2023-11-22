package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TemplateServiceTaskDto {
    private Long testaId;

    @Size(max = 55, message = "Template Service Task Length Exceeded !")
    private String testaName;

    private Long testaGroup;
}
