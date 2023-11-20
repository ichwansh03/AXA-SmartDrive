package com.app.smartdrive.api.dto.master;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    private Long cateId;

    @Size(max = 55, message = "Category Name Length Exceeded !")
    @NotBlank(message = "Category Name Cannot Be Null !")
    private String cateName;
}
