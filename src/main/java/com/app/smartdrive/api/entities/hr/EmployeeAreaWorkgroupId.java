package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
public class EmployeeAreaWorkgroupId implements Serializable {
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eawg_id")
    private Long eawgId;

    @Column(name = "eawg_entityid")
    private Long eawgEntityid;
}
