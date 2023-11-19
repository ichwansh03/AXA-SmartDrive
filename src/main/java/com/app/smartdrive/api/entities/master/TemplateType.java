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
@Table(name = "template_type", schema = "mtr")
public class TemplateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tety_id")
    private int tetyId;

    @Column(name = "tety_name", unique = true)
    private String tety_name;

    @Column(name = "tety_group", unique = true)
    private String tety_group;

    @OneToMany(mappedBy = "template_type", fetch = FetchType.LAZY)
    private List<TemplateServiceTask> templateServiceTasks;
}
