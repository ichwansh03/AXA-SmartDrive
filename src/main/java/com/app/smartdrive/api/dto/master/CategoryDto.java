package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long cateId;

    @Size(max = 55, message = "Category Name Length Exceeded !")
    private String cateName;

    private List<TemplateInsurancePremiDto> templateInsurancePremiDto;
}