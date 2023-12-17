package com.smartdrive.serviceorderservice.services.premi;

import com.smartdrive.serviceorderservice.entities.ServicePremi;

public interface ServPremiService {

    ServicePremi findByServId(Long servId);

    ServicePremi addSemi(ServicePremi servicePremi, Long servId);

    int updateSemiStatus(String semiStatus, Long semiServId);
}
