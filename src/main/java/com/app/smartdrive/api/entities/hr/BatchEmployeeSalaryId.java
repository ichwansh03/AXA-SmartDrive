package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
public class BatchEmployeeSalaryId implements Serializable{
    private Long besaEmpEntityid;

    private LocalDate besaCreatedDate;

}
