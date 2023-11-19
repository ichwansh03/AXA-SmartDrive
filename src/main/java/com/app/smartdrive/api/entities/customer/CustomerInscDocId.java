package com.app.smartdrive.api.entities.customer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInscDocId implements Serializable{
    private Long cadocId;
    private Long cadocCreqEntityid;
}
