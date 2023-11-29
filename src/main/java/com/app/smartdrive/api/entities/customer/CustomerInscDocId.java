package com.app.smartdrive.api.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInscDocId implements Serializable{
    private Long cadocId;
    private Long cadocCreqEntityid;
}
