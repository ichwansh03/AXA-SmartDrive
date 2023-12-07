package com.app.smartdrive.api.dto.master.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateRes {
    private Long cateId;

    @Size(max = 55, message = "Category Name Length Exceeded !")
    private String cateName;
}
