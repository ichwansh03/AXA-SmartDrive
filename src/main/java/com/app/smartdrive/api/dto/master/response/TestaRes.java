package com.app.smartdrive.api.dto.master.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestaRes {
    private Long testaId;

    @Size(max = 55, message = "Template Service Task Length Exceeded !")
    private String testaName;

    private String testaGroup;
    private String testaCallMethod;
    private Integer testaSeqOrder;
    private Integer testaTetyId;
    private TetyRes templateType;
}
