package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.dto.service_order.response.SemiDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServPremiService {

    SemiDto findByServId(Long servId);

    ServicePremi generateServPremi(Services services);

    int updateSemiStatus(String semiStatus, Long semiServId);

    void mapServicePremiToDtoServices(Services services, ServiceRespDto serviceRespDto);
}
