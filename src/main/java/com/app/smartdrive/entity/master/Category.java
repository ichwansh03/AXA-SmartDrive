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
@Table(name = "category", schema = "mtr")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id", updatable = false, nullable = false)
    private int cateId;

    @Column(name = "cate_name")
    private String cate_name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<TemplateInsurancePremi> templateInsurancePremis;
}
