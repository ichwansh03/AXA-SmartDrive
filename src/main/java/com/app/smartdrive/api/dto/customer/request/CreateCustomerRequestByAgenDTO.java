package com.app.smartdrive.api.dto.customer.request;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerRequestByAgenDTO {

    private Long employeeId;

    private Long agenId;

    private Boolean accessGrantUser;

    @Valid
    @NotBlank
    private CiasDTO ciasDTO;

    @Valid
    @NotBlank
    private CreateUserDto userDTO;
}
