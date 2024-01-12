package com.app.smartdrive.api.services.service_order.servorder.services;

import com.app.smartdrive.api.dto.service_order.response.FeasiblityDto;
import com.app.smartdrive.api.dto.service_order.response.PolisDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.services.service_order.servorder.BaseServiceOrder;

import java.util.List;

public interface ServService extends BaseServiceOrder<ServiceRespDto, Long> {

    List<FeasiblityDto> findAllFeasiblity();

    List<PolisDto> findAllPolis();

}
