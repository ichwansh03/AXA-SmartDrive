package com.app.smartdrive.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TemplateServiceTask", schema = "mtr")
public class TemplateServiceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testa_id", updatable = false, nullable = false)
    private int testaId;

    @Column(name = "testa_name")
    private String testaName;

    @Column(name = "testa_group")
    private int testaGroup;

    @ManyToOne
    @JoinColumn(name = "testa_group")
    private TemplateType templateType;

    @OneToMany(mappedBy = "template_service_task", fetch = FetchType.LAZY)
    private List<TemplateTaskWorkOrder> templateTaskWorkOrder;
}
