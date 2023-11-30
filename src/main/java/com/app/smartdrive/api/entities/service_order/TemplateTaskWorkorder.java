package com.app.smartdrive.api.entities.service_order;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateTaskWorkorder {

    @Column(name = "tesa_name")
    @Size(max = 55)
    private String tesaName;
}
