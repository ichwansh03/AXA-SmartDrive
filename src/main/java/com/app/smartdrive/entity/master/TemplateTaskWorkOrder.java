package com.app.smartdrive.entity.master;

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
    private int tewoId;

    @Column(name = "tewo_name")
    private String tewoName;

    @Column(name = "tewo_testa_id")
    private int tewoTestaId;

    @ManyToOne
    @JoinColumn(name = "tewo_testa_id")
    private TemplateServiceTask templateServiceTask;
}
