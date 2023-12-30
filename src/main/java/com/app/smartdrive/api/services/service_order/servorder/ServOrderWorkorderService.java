package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServOrderWorkorderService {

    List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId);

    boolean checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList);

    List<ServiceOrderWorkorder> findAllByPartnerAndArwgCode(Long partnerId, String arwgCode);


}
