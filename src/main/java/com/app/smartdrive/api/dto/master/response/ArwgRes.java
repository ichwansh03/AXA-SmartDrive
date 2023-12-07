package com.app.smartdrive.api.dto.master.response;

import com.app.smartdrive.api.entities.master.Cities;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArwgRes {
    private String arwgCode;

    @Size(max = 55, message = "Area Work Group Description Length Exceeded !")
    private String arwgDesc;

    private CitiesRes cities;
}