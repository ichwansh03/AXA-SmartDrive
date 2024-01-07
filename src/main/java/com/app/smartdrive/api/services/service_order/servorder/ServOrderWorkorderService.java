package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServOrderWorkorderService {

    SoWorkorderDto findWorkorderById(Long sowoId);

    List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId);

    boolean checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList);

    boolean checkWorkorderBySeotId(Long seotId);

    List<ServiceOrderWorkorder> findAllByPartnerAndArwgCode(Long partnerId, String arwgCode);


}
