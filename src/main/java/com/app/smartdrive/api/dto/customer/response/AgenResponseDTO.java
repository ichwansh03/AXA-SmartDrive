package com.app.smartdrive.api.dto.customer.response;


import java.util.List;

import com.app.smartdrive.api.entities.master.Cities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgenResponseDTO {
    private Long agenId;
    private String agenName;
    private String agenEmail;
    private String agenPhone;
    private String agenJoinDate;
    private String agenGraduate;
    private String agenRole;
    private Double agenSalary;
    private String agenAccountNumber;
    private List<Cities> agenCity;
}
