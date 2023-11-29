package com.app.smartdrive.api.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInscExtendId implements Serializable{
    private Long cuexId;
    private Long cuexCreqEntityid;
}
