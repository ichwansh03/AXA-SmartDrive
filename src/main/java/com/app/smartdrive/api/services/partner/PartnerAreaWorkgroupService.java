package com.app.smartdrive.api.services.partner;

import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.services.BaseService;

public interface PartnerAreaWorkgroupService extends BaseService<PartnerAreaWorkgroup, Long> {

    PartnerAreaWorkgroup save(PartnerAreaWorkgroupRequest request);

}
