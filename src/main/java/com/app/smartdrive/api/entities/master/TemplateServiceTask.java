package com.app.smartdrive.api.entities.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "template_service_task", schema = "mtr")
public class TemplateServiceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testa_id", updatable = false, nullable = false)
    private int testaId;

    @Column(name = "testa_name")
    private String testaName;

    @Column(name = "testa_group", insertable = false, nullable = false)
    private int testaGroup;

    @ManyToOne
    @JoinColumn(name = "testa_group")
    private TemplateType templateType;

    @OneToMany(mappedBy = "templateServiceTask", fetch = FetchType.LAZY)
    private List<TemplateTaskWorkOrder> templateTaskWorkOrder;
}
