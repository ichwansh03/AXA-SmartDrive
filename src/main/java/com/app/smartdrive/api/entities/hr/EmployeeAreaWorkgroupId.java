package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAreaWorkgroupId implements Serializable {
    private Long eawgId;
    private Long eawgEntityid;
}
