package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.dto.service_order.request.ServiceReqDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDto extends ServiceReqDto {

    private Long servId;
}
