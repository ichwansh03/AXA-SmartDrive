package com.app.smartdrive.api.entities.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "template_task_workorder", schema = "mtr")
public class TemplateTaskWorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tewo_id", updatable = false, nullable = false)
    private Long tewoId;

    @Column(name = "tewo_name")
    private String tewoName;

    @Column(name = "tewo_testa_id")
    private Long tewoTestaId;

    @Column(name = "tewo_value")
    private String tewoValue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tewo_testa_id", insertable = false, updatable = false)
    private TemplateServiceTask templateServiceTask;
}
