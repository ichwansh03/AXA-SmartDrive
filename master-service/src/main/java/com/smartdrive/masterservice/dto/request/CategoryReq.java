package com.smartdrive.masterservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryReq {
    @Size(max = 55,message = "Category Name Length Exceeded !")
    @NotNull(message = "Category Name Cannot Be Null !")
    private String cateName;
}
